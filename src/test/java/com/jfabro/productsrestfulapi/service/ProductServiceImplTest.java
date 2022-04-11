package com.jfabro.productsrestfulapi.service;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.exceptions.ProductNotFoundException;
import com.jfabro.productsrestfulapi.repository.ProductImageRepository;
import com.jfabro.productsrestfulapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    ProductRepository productRepository;
    @MockBean
    ProductImageRepository productImageRepository;

    @Autowired
    ProductService productService;

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
        when(productImageRepository.findBySku(anyString())).thenReturn(Collections.emptyList());
        ProductsDto productsDto = productService.getAllProducts();

        assertNotNull(productsDto);
        assertEquals(productsDto.getProducts().size(), 1);
        assertEquals(productsDto.getProducts().get(0).getOtherImagesUrl().size(), 0);
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

        //Exception exception = assertThrows(ProductNotFoundException.class, () -> {
        //    productService.getProduct("2");
        //});

        assertThrows(ProductNotFoundException.class, () -> {
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