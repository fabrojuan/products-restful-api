package com.jfabro.productsrestfulapi.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String skuProduct) {
        super("Could not find product " + skuProduct);
    }
}
