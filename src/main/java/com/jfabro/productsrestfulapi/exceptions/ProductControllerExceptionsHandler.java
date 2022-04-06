package com.jfabro.productsrestfulapi.exceptions;

import com.jfabro.productsrestfulapi.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ProductControllerExceptionsHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String errors = ex.getAllErrors()
                .stream()
                .map(p -> p.getDefaultMessage())
                .collect(Collectors.joining(" - "));
        return new ErrorMessage(errors);
    }

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage ProductNotFoundExceptionHandler(ProductNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorMessage DefaultExceptionHandler(Exception ex) {
        log.error("Error {}", ex.getMessage());
        return new ErrorMessage("Internal Server Error");
    }

}
