package com.jfabro.productsrestfulapi.configuration;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ObjectMapperTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void context_loads() {
        assertNotNull(modelMapper);
    }

    @Test
    void test_model_mapper_ProductDto_to_ProductDao() {
        ProductDto productDto = new ProductDto();
        productDto.setSku("1500");
        productDto.setName("test_product");
        productDto.setBrand("test_brand");
        productDto.setSize("test_size");
        productDto.setPrice(new BigDecimal(700));
        productDto.setPrincipalImageUrl("url_principal_image");
        productDto.setOtherImagesUrl(Collections.singletonList("url_other_image_1"));

        ProductDao productDao = modelMapper.map(productDto, ProductDao.class);

        assertNotNull(productDao);
        assertEquals(productDao.getName(), productDto.getName());
        assertEquals(productDao.getBrand(), productDto.getBrand());
        assertEquals(productDao.getSize(), productDto.getSize());
        assertEquals(productDao.getPrice(), productDto.getPrice());
        assertEquals(productDao.getPrincipalImageUrl(), productDto.getPrincipalImageUrl());
        assertNull(productDao.getOtherImagesUrl());
    }
}