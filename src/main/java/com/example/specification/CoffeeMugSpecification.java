package com.example.specification;
import com.example.data.FilterOperation;
import com.example.entity.CoffeeMug;
import com.example.entity.CoffeeMugType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.util.StringUtils;
import io.micronaut.data.jpa.repository.criteria.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;


@Introspected
public class CoffeeMugSpecification {

    public static Specification<CoffeeMug> filterCoffeeMug(
            Optional<FilterOperation> filterOperationOptional,
            Optional<String> displayName,
            Optional<String> mugType,
            BigDecimal minPrice,
            BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {

            FilterOperation filterOperation = filterOperationOptional.orElse(FilterOperation.AND);

            List<Predicate> predicates = new ArrayList<>();

            Join<CoffeeMug, CoffeeMugType> joinCoffeeMugType = root.join("coffeeMugType", JoinType.INNER);

            if (displayName.isPresent() && StringUtils.hasText(displayName.get())) {
                predicates.add(criteriaBuilder.like( criteriaBuilder.upper(root.get("displayName")),
                        "%" + displayName.get().toUpperCase() + "%"
                ));
            }

            if (mugType.isPresent() && StringUtils.hasText(mugType.get())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(joinCoffeeMugType.get("name")),
                        "%" + mugType.get().toUpperCase() + "%"
                ));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (FilterOperation.AND.equals(filterOperation)) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}

