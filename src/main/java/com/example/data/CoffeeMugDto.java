package com.example.data;

import com.example.entity.CoffeeMugType;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Serdeable.Serializable
@Serdeable.Deserializable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoffeeMugDto {

    private Long id;

    int quantity;

    int count;

    String skuCode;

    String displayName;

    BigDecimal price;

    String coffeeMugType;
}
