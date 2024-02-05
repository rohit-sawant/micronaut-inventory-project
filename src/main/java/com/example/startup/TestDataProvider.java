package com.example.startup;

import com.example.entity.CoffeeMug;
import com.example.entity.CoffeeMugType;
import com.example.repository.CoffeeMugRepository;
import com.example.repository.CoffeeMugTypeRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Singleton
@Requires(notEnv = Environment.TEST)
public class TestDataProvider {

    @Inject
    CoffeeMugRepository coffeeMugRepository;


    @Inject
    CoffeeMugTypeRepository coffeeMugTypeRepository;
    @EventListener
    public void init(StartupEvent event){

        List<CoffeeMugType> coffeeMugTypes = coffeeMugTypeRepository.findAll();
        if(coffeeMugTypes.isEmpty()){
            coffeeMugTypes = Stream.of(
                            new CoffeeMugType("Espresso"),
                            new CoffeeMugType("Cappuccino"),
                            new CoffeeMugType("Flat white")
                    )
                    .map(coffeeMugTypeRepository::save)
                    .toList();
        }
        if(coffeeMugRepository.findAll().isEmpty()){
            AtomicInteger id = new AtomicInteger(1);
            List<CoffeeMug> coffeeMugs = coffeeMugTypes.stream()
                    .flatMap(coffeeMugType -> {
                        CoffeeMug coffeeMug1 = new CoffeeMug(10, 100, "SKU001", "Espresso Cup "+String.valueOf(id), BigDecimal.valueOf(2.5), coffeeMugType);
                        CoffeeMug coffeeMug2 = new CoffeeMug(8, 80, "SKU002", "Cappuccino Cup "+String.valueOf(id), BigDecimal.valueOf(3.0), coffeeMugType);
                        CoffeeMug coffeeMug4 = new CoffeeMug(12, 120, "SKU003", "Flat White Cup "+String.valueOf(id), BigDecimal.valueOf(3.5), coffeeMugType);
                        id.getAndIncrement();
                        return Stream.of(coffeeMug1, coffeeMug2, coffeeMug4);
                    })
                    .map(coffeeMugRepository::save)
                    .toList();
        }

    }
}
