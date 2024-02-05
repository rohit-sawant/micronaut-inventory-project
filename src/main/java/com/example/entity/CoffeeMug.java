package com.example.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Serdeable.Serializable
@Serdeable.Deserializable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class CoffeeMug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    int quantity;

    int count;

    String skuCode;

    @Column(name = "display_name")
    String displayName;

    BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "coffee_mug_type_id")
    CoffeeMugType coffeeMugType;


    public CoffeeMug(int quantity, int count, String skuCode, String displayName, BigDecimal price) {
        this.quantity = quantity;
        this.count = count;
        this.skuCode = skuCode;
        this.displayName = displayName;
        this.price = price;
    }
    public CoffeeMug(int quantity, int count, String skuCode, String displayName, BigDecimal price,CoffeeMugType coffeeMugType) {
        this.quantity = quantity;
        this.count = count;
        this.skuCode = skuCode;
        this.displayName = displayName;
        this.price = price;
        this.coffeeMugType = coffeeMugType;
    }
}
