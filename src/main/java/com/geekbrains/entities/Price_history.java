package com.geekbrains.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@NoArgsConstructor
public class Price_history {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private BigDecimal price;

    @Override
    public String toString() {
        return String.valueOf(this.price);
    }
}
