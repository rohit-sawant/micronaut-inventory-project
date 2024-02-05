package com.example.data;

import io.micronaut.serde.annotation.Serdeable;
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
public class CoffeeMugRequestDto {


    int quantity;

    int count;

    String skuCode;

    String displayName;

    BigDecimal price;

    String coffeeMugType;
}
