package com.example.controller;

import com.example.data.CoffeeMugRequestDto;
import com.example.entity.CoffeeMug;
import com.example.entity.CoffeeMugType;
import com.example.repository.CoffeeMugRepository;
import com.example.repository.CoffeeMugTypeRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.transaction.SynchronousTransactionManager;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@MicronautTest
public class CoffeeMugControllerTest {

    @Inject
    @Client("/coffee_mug")
    HttpClient client;

    @Inject
    CoffeeMugTypeRepository coffeeMugTypeRepository;

    @Inject
    CoffeeMugRepository coffeeMugRepository;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    CoffeeMugController coffeeMugController;
    private List<CoffeeMug> addedCoffeeMugs;

    private List<CoffeeMugType> addedCoffeeMugTypes;


    @BeforeEach
    void setUp() {
        addedCoffeeMugTypes = Stream.of(
                        new CoffeeMugType("Espresso Test"),
                        new CoffeeMugType("Cappuccino Test"),
                        new CoffeeMugType("Flat white Test")
                )
                .map(coffeeMugTypeRepository::save)
                .toList();


        addedCoffeeMugs = addedCoffeeMugTypes.stream()
                .flatMap(coffeeMugType -> {
                    CoffeeMug coffeeMug1 = new CoffeeMug(10, 100, "SKU001", "Espresso Cup Test", BigDecimal.valueOf(2.5), coffeeMugType);
                    CoffeeMug coffeeMug2 = new CoffeeMug(8, 80, "SKU002", "Cappuccino Cup Test", BigDecimal.valueOf(3.0), coffeeMugType);
                    CoffeeMug coffeeMug4 = new CoffeeMug(12, 120, "SKU003", "Flat White Cup Test", BigDecimal.valueOf(3.5), coffeeMugType);
                    return Stream.of(coffeeMug1, coffeeMug2, coffeeMug4);
                })
                .map(coffeeMugRepository::save)
                .toList();

    }

    @AfterEach
    void tearDown() {
        coffeeMugRepository.deleteAll();
        coffeeMugTypeRepository.deleteAll(addedCoffeeMugTypes);
    }

    @Test
    void testAddCoffee() throws Exception {
        CoffeeMugRequestDto coffeeMugRequestDto = new CoffeeMugRequestDto(10, 10, "A0012", "Product 1", new BigDecimal("34.50"), addedCoffeeMugTypes.get(0).getName());

        var response = client.toBlocking()
                .exchange(HttpRequest.POST("", coffeeMugRequestDto)
                        .contentType(MediaType.APPLICATION_JSON), JsonNode.class);
        CoffeeMug coffeeMug = objectMapper.readValueFromTree(response.getBody().get(), CoffeeMug.class);

        assertEquals(HttpStatus.CREATED, response.status());
        assertEquals(coffeeMug.getCoffeeMugType().getName(), addedCoffeeMugTypes.get(0).getName());

    }

    @Test
    public void filterCoffees() throws IOException {
        List<CoffeeMug> filteredCoffeeMugs;
        //      Test error handling for invalid filter operations
        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().retrieve(
                    HttpRequest.GET("?filterOperation=INVALID"),
                    Argument.listOf(CoffeeMug.class)
            );
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

//      Test filtering by display name
         filteredCoffeeMugs = client.toBlocking().retrieve(
                HttpRequest.GET("?displayName=Espresso"),
                Argument.listOf(CoffeeMug.class)
        );
        assertNotNull(filteredCoffeeMugs);
        assertEquals(filteredCoffeeMugs.size(),3);

        filteredCoffeeMugs = client.toBlocking().retrieve(
                HttpRequest.GET("?mugType=Espresso"),
                Argument.listOf(CoffeeMug.class)
        );
        assertNotNull(filteredCoffeeMugs);
        assertEquals(filteredCoffeeMugs.size(),3);

//        Test filtering with multiple criteria
        filteredCoffeeMugs = client.toBlocking().retrieve(
                HttpRequest.GET("?displayName=Test&priceRange=2.50-3.00"),
                Argument.listOf(CoffeeMug.class)
        );
        assertEquals(filteredCoffeeMugs.size(),6);

//       Test filtering with multiple criteria with OR
        filteredCoffeeMugs = client.toBlocking().retrieve(
                HttpRequest.GET("?displayName=Test&priceRange=2.50-3.00&filterOperation=OR"),
                Argument.listOf(CoffeeMug.class)
        );
        assertEquals(filteredCoffeeMugs.size(),9);



    }

}

