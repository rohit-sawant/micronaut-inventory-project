package com.example.exception;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Serdeable
public class InvalidFilterOperation extends RuntimeException {

    public InvalidFilterOperation(String message) {
        super(message);
    }

    // You can add more constructors or methods as needed
}

