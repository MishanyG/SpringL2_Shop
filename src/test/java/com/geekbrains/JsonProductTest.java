package com.geekbrains;

import com.geekbrains.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class JsonProductTest {

    @Autowired
    private JacksonTester <Product> jacksonProd;

    @Test
    public void jsonSerializationProductTest() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Bread");
        product.setPrice(new BigDecimal("15.13"));
        assertThat(this.jacksonProd.write(product)).hasJsonPathNumberValue("$.id");
        assertThat(this.jacksonProd.write(product)).extractingJsonPathStringValue("$.title").isEqualTo("Bread");
        assertThat(this.jacksonProd.write(product)).hasJsonPathNumberValue("$.price");
    }

    @Test
    public void jsonDeserializationProductTest() throws Exception {
        String content = "{\"id\": 2,\"title\":\"Meat\",\"price\": \"165.40\"}";
        Product realProduct = new Product();
        realProduct.setId(2L);
        realProduct.setTitle("Meat");
        realProduct.setPrice(new BigDecimal("165.40"));
        assertThat(this.jacksonProd.parseObject(content).getId()).isEqualTo(2L);
        assertThat(this.jacksonProd.parseObject(content).getTitle()).isEqualTo("Meat");
        assertThat(this.jacksonProd.parseObject(content).getPrice()).isEqualTo("165.40");
    }
}
