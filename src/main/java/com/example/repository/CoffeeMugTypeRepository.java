package com.example.repository;

import com.example.entity.CoffeeMug;
import com.example.entity.CoffeeMugType;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

@Repository
public interface CoffeeMugTypeRepository extends JpaRepository<CoffeeMugType, Long> {


    public Optional<CoffeeMugType> findByName(String name);

}