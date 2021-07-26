package com.geekbrains.frontend;

import com.geekbrains.entities.Category;
import com.geekbrains.entities.Product;
import com.geekbrains.repositories.CategoryRepository;
import com.geekbrains.repositories.ProductRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

@Route("create-product")
public class CreateProductView extends AbstractView {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CreateProductView(CategoryRepository categoryRepository,
                             ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        initProductView();
    }

    private void initProductView() {
        TextField titleTextField = initTextFieldWithPlaceholder("Заголовок");
        TextField priceTextField = initTextFieldWithPlaceholder("Цена");
        MultiSelectListBox<Category> categoryMultiSelectListBox = new MultiSelectListBox<>();
        categoryMultiSelectListBox.setItems(new HashSet<>(categoryRepository.findAll()));

        Button saveButton = new Button("Сохранить", event -> {
            Product product = new Product();
            product.setTitle(titleTextField.getValue());
            product.setPrice(new BigDecimal(priceTextField.getValue()));
            product.setCategories(new ArrayList<>(categoryMultiSelectListBox.getValue()));

            productRepository.save(product);
            UI.getCurrent().navigate("market");
        });

        Button backButton = new Button("Назад", event -> {
            UI.getCurrent().navigate("market");
        });

        add(titleTextField, priceTextField, categoryMultiSelectListBox, saveButton, backButton);
    }
}
