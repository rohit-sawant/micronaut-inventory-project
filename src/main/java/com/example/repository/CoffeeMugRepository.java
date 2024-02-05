package com.example.repository;

import com.example.entity.CoffeeMug;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.jpa.repository.JpaSpecificationExecutor;
import io.micronaut.data.jpa.repository.criteria.Specification;
import io.micronaut.http.annotation.QueryValue;

import java.util.List;

@Repository
public interface CoffeeMugRepository extends JpaRepository<CoffeeMug, Long>, JpaSpecificationExecutor<CoffeeMug> {



}