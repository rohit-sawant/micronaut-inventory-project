package com.example.exception;


import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class InvalidFilterOperationExceptionHandlerInterceptor implements ExceptionHandler<InvalidFilterOperation, HttpResponse<CustomError>> {

    @Override
    public HttpResponse<CustomError> handle(HttpRequest request, InvalidFilterOperation exception) {
        CustomError customError = new CustomError(  exception.getMessage());
        return HttpResponse.<CustomError>badRequest().body(customError);
    }
}

