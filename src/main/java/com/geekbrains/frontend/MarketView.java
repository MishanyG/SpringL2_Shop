package com.geekbrains.frontend;

import com.geekbrains.entities.Product;
import com.geekbrains.repositories.ProductRepository;
import com.geekbrains.services.CartService;
import com.geekbrains.specifications.ProductFilter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@Route("market")
@CssImport("./themes/styles.css")
public class MarketView extends AbstractView {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final Authentication authentication;

    private TextField titleTextField;
    private TextField minPriceTextField;
    private TextField maxPriceTextField;
    private Grid<Product> productGrid;

    public MarketView(CartService cartService,
                      ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        initMarketPage();
    }

    private void initMarketPage() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Button("Главная", e -> System.out.println("Главная")));
        horizontalLayout.add(new Button("Корзина", e -> UI.getCurrent().navigate("cart")));
        horizontalLayout.add(new Button("Мои заказы", e -> UI.getCurrent().navigate("orders")));
        Button otherOrdersButton = new Button("Заказы пользователей", e -> UI.getCurrent().navigate("other-orders"));

        if (isManagerOrAdmin()) {
            horizontalLayout.add(new Button("Добавить продукт", e -> UI.getCurrent().navigate("create-product")));
            horizontalLayout.add(otherOrdersButton);
        }

        horizontalLayout.add(new Button("Выход", e -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("login");
        }));

        productGrid = new Grid<>(Product.class);
        productGrid.setWidth("60%");
        productGrid.setColumns("id", "title", "price");
        productGrid.getColumns().get(0).setHeader("ИД");
        productGrid.getColumns().get(1).setHeader("Название");
        productGrid.getColumns().get(2).setHeader("Цена");
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        List<Product> productList = productRepository.findAll();
        productGrid.setItems(productList);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        setHorizontalComponentAlignment(Alignment.CENTER, productGrid);
        add(horizontalLayout);
        HorizontalLayout filterAndProductGridComponentsLayout = new HorizontalLayout(initFilterComponent(), productGrid);
        filterAndProductGridComponentsLayout.setWidth("100%");
        add(filterAndProductGridComponentsLayout);

        add(new Button("Добавить выбранные товары", e -> {
            Set<Product> productSet = productGrid.getSelectedItems();
            productSet.stream().forEach(cartService::add);
            Notification.show("Товар успешно добавлен в корзину");
        }));
    }

    private boolean isManagerOrAdmin() {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")
                || a.getAuthority().equals("ROLE_ADMIN"));
    }

    private Component initFilterComponent() {
        VerticalLayout priceLayout = new VerticalLayout();
        titleTextField = initTextFieldWithPlaceholder("Название");
        titleTextField.setWidth("100%");
        minPriceTextField = initTextFieldWithPlaceholder("Минимальная цена");
        minPriceTextField.setWidth("100%");
        maxPriceTextField = initTextFieldWithPlaceholder("Максимальная цена");
        maxPriceTextField.setWidth("100%");
        priceLayout.add(titleTextField, minPriceTextField, maxPriceTextField);
        priceLayout.setAlignItems(Alignment.CENTER);
        priceLayout.setWidth("100%");

        VerticalLayout categoriesLayout = new VerticalLayout();
        Label categoriesLabel = new Label("Категории");
        categoriesLabel.setWidth("100%");
        categoriesLabel.addClassName("center");
        Checkbox bakingCheckbox = new Checkbox("Хлебобулочные изделия");
        Checkbox devicesCheckbox = new Checkbox("Устройства");
        Checkbox milkCheckbox = new Checkbox("Молочная продукция");
        Checkbox fruitsCheckbox = new Checkbox("Фрукты");
        categoriesLayout.add(categoriesLabel, bakingCheckbox, devicesCheckbox, milkCheckbox, fruitsCheckbox);
        categoriesLayout.setAlignItems(Alignment.START);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button makeFiltersButton = new Button("Применить", event -> {
            Map<String, String> filterMap = new HashMap<>();
            List<String> categories = new ArrayList<>();
            if (StringUtils.isNotEmpty(titleTextField.getValue())) {
                filterMap.put("title", titleTextField.getValue());
            }

            if (StringUtils.isNotEmpty(minPriceTextField.getValue())) {
                filterMap.put("min_price", minPriceTextField.getValue());
            }

            if (StringUtils.isNotEmpty(maxPriceTextField.getValue())) {
                filterMap.put("max_price", maxPriceTextField.getValue());
            }

            if (bakingCheckbox.getValue()) {
                categories.add("Baking");
            }

            if (devicesCheckbox.getValue()) {
                categories.add("Devices");
            }

            if (milkCheckbox.getValue()) {
                categories.add("Milk");
            }

            if (fruitsCheckbox.getValue()) {
                categories.add("Fruits");
            }

            productGrid.setItems(
                    productRepository.findAll(
                            new ProductFilter(filterMap, categories).getSpec(), PageRequest.of(0, 10)).getContent()
            );
        });

        Button cancelFiltersButton = new Button("Сброс фильтра", event -> productGrid.setItems(productRepository.findAll()));
        buttonLayout.add(makeFiltersButton, cancelFiltersButton);
        buttonLayout.setMargin(false);

        VerticalLayout filterComponentsLayout = new VerticalLayout(priceLayout, categoriesLayout, buttonLayout);
        filterComponentsLayout.setAlignItems(Alignment.START);
        filterComponentsLayout.setWidth(null);
        filterComponentsLayout.addClassName("outline");
        return filterComponentsLayout;
    }

}
