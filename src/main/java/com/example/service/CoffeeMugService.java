package com.example.service;

import com.example.convertor.CoffeeMugDtoConvertor;
import com.example.data.CoffeeMugRequestDto;
import com.example.entity.CoffeeMug;
import com.example.entity.CoffeeMugType;
import com.example.repository.CoffeeMugRepository;
import com.example.repository.CoffeeMugTypeRepository;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class CoffeeMugService {

    @Inject
    CoffeeMugRepository coffeeMugRepository;


    @Inject
    CoffeeMugTypeRepository coffeeMugTypeRepository;
    public CoffeeMug add(CoffeeMugRequestDto coffeeMugRequestDto){
        Optional<CoffeeMugType> coffeeMugTypeOptional = this.coffeeMugTypeRepository.findByName(coffeeMugRequestDto.getCoffeeMugType());
        CoffeeMugType coffeeMugType;
        if(coffeeMugTypeOptional.isPresent()){
            coffeeMugType = coffeeMugTypeOptional.get();
        }
        else{
            coffeeMugType = this.coffeeMugTypeRepository.save(new CoffeeMugType(coffeeMugRequestDto.getCoffeeMugType()));
        }
        CoffeeMug coffeeMug = CoffeeMugDtoConvertor.convert(coffeeMugRequestDto);
        coffeeMug.setCoffeeMugType(coffeeMugType);

        return this.coffeeMugRepository.save(coffeeMug);
    }

}
