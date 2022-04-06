package com.jfabro.productsrestfulapi.repository;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void save_method() {
        ProductDao product = new ProductDao();
        product.setName("shoes");
        product.setBrand("adidas");
        product.setSize("11");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setPrincipalImageUrl("http://image1.img");

        ProductDao savedProduct = productRepository.save(product);

        assertEquals(savedProduct.getName(),"shoes");
        assertEquals(savedProduct.getBrand(), "adidas");
        assertEquals(savedProduct.getSize(), "11");
        assertEquals(savedProduct.getPrice(), BigDecimal.valueOf(1500));
        assertEquals(savedProduct.getPrincipalImageUrl(),"http://image1.img");

    }

    @Test
    public void get_product_by_id_and_found() {
        ProductDao product = new ProductDao();
        product.setName("shoes");
        product.setBrand("adidas");
        product.setSize("11");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setPrincipalImageUrl("http://image1.img");

        productRepository.save(product);
        ProductDao bdProduct = productRepository.findById("1").get();

        assertNotNull(bdProduct);

    }

    @Test
    public void get_product_by_id_and_not_found() {
        ProductDao product = new ProductDao();
        product.setName("shoes");
        product.setBrand("adidas");
        product.setSize("11");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setPrincipalImageUrl("http://image1.img");

        productRepository.save(product);
        ProductDao bdProduct = productRepository.findById("2").orElse(null);

        assertNull(bdProduct);

    }

    @Test
    public void get_all_products() {
        ProductDao product = new ProductDao();
        product.setName("shoes");
        product.setBrand("adidas");
        product.setSize("11");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setPrincipalImageUrl("http://image1.img");
        productRepository.save(product);

        ProductDao product2 = new ProductDao();
        product2.setName("jeans");
        product2.setBrand("levis");
        product2.setSize("l");
        product2.setPrice(BigDecimal.valueOf(1300));
        product2.setPrincipalImageUrl("http://image2.img");
        productRepository.save(product2);

        List<ProductDao> products = (List<ProductDao>) productRepository.findAll();

        assertNotNull(products);
        assertEquals(products.size(), 2);

    }



}