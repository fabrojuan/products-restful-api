package com.jfabro.productsrestfulapi.repository;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void save_method() {
        // Given
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
        ProductDao productToSave = new ProductDao();
        productToSave.setName("shoes");
        productToSave.setBrand("adidas");
        productToSave.setSize("11");
        productToSave.setPrice(BigDecimal.valueOf(1500));
        productToSave.setPrincipalImageUrl("http://image1.img");

        ProductDao savedProduct = productRepository.save(productToSave);
        Optional<ProductDao> queriedProduct = productRepository.findById(savedProduct.getSku());

        assertTrue(queriedProduct.isPresent());
        assertEquals("shoes", queriedProduct.orElseThrow().getName());
        assertEquals("adidas", queriedProduct.orElseThrow().getBrand());
        assertEquals("11", queriedProduct.orElseThrow().getSize());
        assertEquals(BigDecimal.valueOf(1500), queriedProduct.orElseThrow().getPrice());
        assertEquals("http://image1.img", queriedProduct.orElseThrow().getPrincipalImageUrl());

    }

    @Test
    public void get_product_by_id_and_not_found() {
        Optional<ProductDao> product = productRepository.findById("-1");
        assertFalse(product.isPresent());

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

    @Test
    public void update_product() {
        // Given
        ProductDao product = new ProductDao();
        product.setName("shoes");
        product.setBrand("adidas");
        product.setSize("11");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setPrincipalImageUrl("http://image1.img");

        ProductDao savedProduct = productRepository.save(product);
        String originalSku = savedProduct.getSku();

        assertEquals(savedProduct.getName(),"shoes");
        assertEquals(savedProduct.getBrand(), "adidas");
        assertEquals(savedProduct.getSize(), "11");
        assertEquals(savedProduct.getPrice(), BigDecimal.valueOf(1500));
        assertEquals(savedProduct.getPrincipalImageUrl(),"http://image1.img");

        savedProduct.setName("updated shoes");
        savedProduct.setBrand("updated adidas");
        savedProduct.setSize("updated 11");
        savedProduct.setPrice(BigDecimal.valueOf(1501));
        savedProduct.setPrincipalImageUrl("http://updated_image1.img");

        ProductDao resavedProduct = productRepository.save(savedProduct);
        String updatedSku = resavedProduct.getSku();

        assertEquals(originalSku, updatedSku);
        assertEquals(savedProduct.getName(),"updated shoes");
        assertEquals(savedProduct.getBrand(), "updated adidas");
        assertEquals(savedProduct.getSize(), "updated 11");
        assertEquals(savedProduct.getPrice(), BigDecimal.valueOf(1501));
        assertEquals(savedProduct.getPrincipalImageUrl(),"http://updated_image1.img");
    }

    @Test
    public void delete_product() {
        // Given
        ProductDao product = new ProductDao();
        product.setName("shoes");
        product.setBrand("adidas");
        product.setSize("11");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setPrincipalImageUrl("http://image1.img");

        ProductDao savedProduct = productRepository.save(product);
        String sku = savedProduct.getSku();

        productRepository.deleteById(sku);

        assertThrows(NoSuchElementException.class, () -> {
           productRepository.findById(sku).orElseThrow();
        });
    }

}