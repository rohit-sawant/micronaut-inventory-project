package com.example.convertor;

import com.example.data.CoffeeMugRequestDto;
import com.example.entity.CoffeeMug;

public class CoffeeMugDtoConvertor {
    public static CoffeeMug convert(CoffeeMugRequestDto coffeeMugRequestDto){
        return CoffeeMug
            .builder()
            .quantity(coffeeMugRequestDto.getQuantity())
            .count(coffeeMugRequestDto.getCount())
            .skuCode(coffeeMugRequestDto.getSkuCode())
            .displayName(coffeeMugRequestDto.getDisplayName())
            .price(coffeeMugRequestDto.getPrice())
            .build();
    }


}
