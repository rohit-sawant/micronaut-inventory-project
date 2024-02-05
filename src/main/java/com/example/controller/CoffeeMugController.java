package com.example.controller;

import com.example.data.CoffeeMugDto;
import com.example.data.CoffeeMugRequestDto;
import com.example.data.FilterOperation;
import com.example.data.Range;
import com.example.entity.CoffeeMug;
import com.example.exception.InvalidFilterOperation;
import com.example.repository.CoffeeMugRepository;
import com.example.service.CoffeeMugService;
import com.example.specification.CoffeeMugSpecification;
import io.micronaut.data.jpa.repository.criteria.Specification;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.hibernate.sql.exec.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Singleton
@Controller("/coffee_mug")
public class CoffeeMugController {
    private static final Logger LOG =  LoggerFactory.getLogger(CoffeeMugController.class);

    @Inject
    CoffeeMugRepository coffeeMugRepository;

    @Inject
    CoffeeMugService coffeeMugService;

    @Status(HttpStatus.CREATED)
    @Post(consumes = MediaType.APPLICATION_JSON)
    public CoffeeMug add(@Body CoffeeMugRequestDto coffeeMugRequestDto){
       return this.coffeeMugService.add(coffeeMugRequestDto);
    }

    @Get
    @Transactional
    public List<CoffeeMug> filterCoffeesMugController(
            @QueryValue Optional<String> displayName,
            @QueryValue Optional<String>  mugType,
            @QueryValue Optional<String> priceRange,
            @QueryValue("filterOperation") Optional<String> filterOperationOptionalStr
    )  {

        Specification<CoffeeMug> spec =  filterCoffeeMugs(displayName, mugType, priceRange, filterOperationOptionalStr);
        return  coffeeMugRepository.findAll(spec);
    }

    @Get("/pagination")
    @Transactional
    public Page<CoffeeMug> filterCoffeeMugsControllerWithPagination(
            @QueryValue Optional<String> displayName,
            @QueryValue Optional<String>  mugType,
            @QueryValue Optional<String> priceRange,
            @QueryValue("filterOperation") Optional<String> filterOperationOptionalStr,
            @QueryValue Optional<Integer> page,
            @QueryValue Optional<Integer> size
    ) {

        Specification<CoffeeMug> spec = filterCoffeeMugs(displayName, mugType, priceRange, filterOperationOptionalStr);

        Pageable pageable = null ;

        if(page.isPresent() && size.isPresent()){
            pageable = Pageable.from(page.get(),size.get());
        }
        else if(size.isPresent()){
            pageable = Pageable.from(page.orElse(0),size.get());
        }

        return  coffeeMugRepository.findAll(spec,pageable);
    }

//    this will extract specification
    private Specification<CoffeeMug> filterCoffeeMugs(@QueryValue Optional<String> displayName, @QueryValue Optional<String> mugType, @QueryValue Optional<String> priceRange, @QueryValue("filterOperation") Optional<String> filterOperationOptionalStr) {
        Optional<FilterOperation> filterOperation = Optional.empty();
        if(filterOperationOptionalStr.isPresent()) filterOperation = Optional.of(FilterOperation.getValue(filterOperationOptionalStr.get()));
        Range range = getMinAndMaxFromRange(priceRange.orElse(null));
        return CoffeeMugSpecification.filterCoffeeMug(filterOperation,displayName, mugType, range.getMin(), range.getMax());
    }

//    CREATE RANGE FROM PRICE RANGE
    private Range getMinAndMaxFromRange(String priceRange){
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        if (priceRange != null && priceRange.matches("^(?:(\\d+(\\.\\d+)?)|(\\d+(\\.\\d+)?)-(\\d+(\\.\\d+)?))$")) {
            String[] rangeParts = priceRange.split("-");
            if (rangeParts.length == 2) {
                // Both lower and upper bounds are provided
                if(!rangeParts[0].isEmpty()) minPrice = new BigDecimal(rangeParts[0].trim());
                if(!rangeParts[1].isEmpty()) maxPrice = new BigDecimal(rangeParts[1].trim());

            } else if (rangeParts.length == 1 && !rangeParts[0].trim().isEmpty()) {
                // Only one numeric value is provided
                minPrice =  new BigDecimal(rangeParts[0].trim());
            }
        }
        return new Range(minPrice,maxPrice);
    }





}
