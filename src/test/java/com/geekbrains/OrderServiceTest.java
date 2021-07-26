package com.geekbrains;

import com.geekbrains.entities.Order;
import com.geekbrains.entities.Product;
import com.geekbrains.entities.Role;
import com.geekbrains.entities.User;
import com.geekbrains.repositories.OrderRepository;
import com.geekbrains.services.CartService;
import com.geekbrains.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.geekbrains.entities.Order.Status.MANAGING;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void addOrderTest() {
        cartService.setAddress("Moscow");
        cartService.setPhone("1234567890");

        User user = userService.findById(1L);

        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setPrice(new BigDecimal(100 + (long) i * 10));
            product.setTitle("Product #" + (long) i);

            cartService.add(product);
        }

        Order order = new Order();
        order.setItems(cartService.getItems());
        order.setAddress(cartService.getAddress());
        order.setPhoneNumber(cartService.getPhone());
        order.setUser(user);
        order.setPrice(cartService.getPrice());
        order.setStatus(MANAGING);
        order.setPhoneNumber(user.getPhone());

        List <Order> orders = orderRepository.findAll();

        Assertions.assertNotNull(orders);
        Assertions.assertEquals(3, cartService.getItems().size());
        Assertions.assertEquals(3, order.getItems().size());
        Assertions.assertEquals("Moscow", order.getAddress());
    }
}
