package com.example.exception;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;


@Singleton
public class CustomErrorResponseProcessor implements ErrorResponseProcessor<CustomError> {
    @Override
    public @NonNull MutableHttpResponse<CustomError> processResponse(@NonNull ErrorContext errorContext, @NonNull MutableHttpResponse<?> baseResponse) {
        CustomError customError;
        if(!errorContext.hasErrors()){
            customError = new CustomError("No custom error found");
        }
        else{
            var firstError = errorContext.getErrors().get(0);
            customError = new CustomError(firstError.getMessage());
        }
        return baseResponse.status(HttpStatus.BAD_REQUEST).body(customError).contentType(MediaType.APPLICATION_JSON);
    }
}
