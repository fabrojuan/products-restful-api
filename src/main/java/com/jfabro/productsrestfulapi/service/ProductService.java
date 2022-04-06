package com.jfabro.productsrestfulapi.service;

import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;


public interface ProductService {
    ProductsDto getAllProducts();
    ProductDto getProduct(String idProduct);
    ProductDto saveProduct(ProductDto product);
    void deleteProduct(String idProduct);
}
