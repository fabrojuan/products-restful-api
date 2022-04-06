package com.jfabro.productsrestfulapi.service;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.exceptions.ProductNotFoundException;
import com.jfabro.productsrestfulapi.repository.ProductImageRepository;
import com.jfabro.productsrestfulapi.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductImageRepository productImageRepository;

    ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductServiceImpl(productRepository, productImageRepository);
    }

    @Test
    void get_all_products() {
        ProductDao productDao = new ProductDao();
        productDao.setSku("1");
        productDao.setName("shirt");
        productDao.setBrand("legacy");
        productDao.setSize("m");
        productDao.setPrice(BigDecimal.valueOf(900));
        productDao.setPrincipalImageUrl("http://image1.img");

        when(productRepository.findAll()).thenReturn(Collections.singleton(productDao));
        ProductsDto productsDto = productService.getAllProducts();

        assertNotNull(productsDto);
        assertEquals(productsDto.getProducts().size(), 1);
    }

    @Test
    void get_one_product_and_found() {
        ProductDao productDao = new ProductDao();
        productDao.setSku("1");
        productDao.setName("shirt");
        productDao.setBrand("legacy");
        productDao.setSize("m");
        productDao.setPrice(BigDecimal.valueOf(900));
        productDao.setPrincipalImageUrl("http://image1.img");

        when(productRepository.findById("1")).thenReturn(java.util.Optional.of(productDao));
        ProductDto productDto = productService.getProduct("1");

        assertEquals(productDto.getName(), "shirt");
    }

    @Test
    void get_one_product_and_not_found() {
        when(productRepository.findById("2")).thenReturn(java.util.Optional.ofNullable(null));

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProduct("2");
        });
    }

    @Test
    void saveProduct() {

    }

    @Test
    void deleteProduct() {
    }
}