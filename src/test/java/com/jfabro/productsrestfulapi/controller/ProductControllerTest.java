package com.jfabro.productsrestfulapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.exceptions.ProductNotFoundException;
import com.jfabro.productsrestfulapi.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_get_all_products() throws Exception {
        // Given
        when(productService.getAllProducts()).thenReturn(getProducts());

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products[0].name").value("shoes"))
                .andExpect(jsonPath("$.products[0].brand").value("caterpillar"))
                .andExpect(jsonPath("$.products[1].size").value("l"))
                .andExpect(jsonPath("$.products[1].price").value(new BigDecimal(350)))
                .andExpect(jsonPath("$.products", hasSize(2)))
                //.andExpect(content().json(aca_va_json_a_comparar))
        ;

        // same: verify(productService).getAllProducts();
        verify(productService, times(1)).getAllProducts();

    }

    private ProductsDto getProducts() {
        ProductsDto products = new ProductsDto();
        products.setProducts(new ArrayList<>());

        ProductDto product1 = new ProductDto();
        product1.setSku("1");
        product1.setName("shoes");
        product1.setBrand("caterpillar");
        product1.setSize("11");
        product1.setPrice(new BigDecimal(850));
        products.getProducts().add(product1);

        ProductDto product2 = new ProductDto();
        product2.setSku("2");
        product2.setName("jeans");
        product2.setBrand("legacy");
        product2.setSize("l");
        product2.setPrice(new BigDecimal(350));
        products.getProducts().add(product2);

        return products;
    }

    @Test
    void test_get_product_and_found() throws Exception {
        // Given
        when(productService.getProduct(anyString())).thenReturn(getProducts().getProducts().get(0));

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("shoes"))
                .andExpect(jsonPath("$.brand").value("caterpillar"));
    }

    @Test
    void test_get_product_and_not_found() throws Exception {
        // Given
        when(productService.getProduct(anyString())).thenThrow(new ProductNotFoundException("50"));
        //.then(invocation -> {
        //    String idProduct = invocation.getArgument(0);
        //    return new ProductNotFoundException(idProduct);
        //}); //.thenThrow(new ProductNotFoundException("50"));

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/50")
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Could not find product 50")));
    }

    @Test
    void test_save_product() throws Exception {
        // When
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getProductToSave())))
        // Then
                .andExpect(status().isCreated());
    }

    @Test
    void test_save_product_with_errors() throws Exception {
        // When
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getProductWithErrorsToSave())))
                // Then
                .andExpect(status().isBadRequest());
    }

    private ProductDto getProductToSave() {
        ProductDto product = new ProductDto();
        product.setName("product");
        product.setBrand("brand");
        product.setSize("s");
        product.setPrice(new BigDecimal(1));
        product.setPrincipalImageUrl("http://image1_product.png");
        product.setOtherImagesUrl(List.of("http://image2_product.png", "http://image3_product.png"));
        return product;
    }

    private ProductDto getProductWithErrorsToSave() {
        ProductDto product = new ProductDto();
        product.setName("pr");
        product.setSize("s");
        product.setPrice(new BigDecimal(1));
        product.setPrincipalImageUrl("http://image1_product.png");
        product.setOtherImagesUrl(List.of("http://image2_product.png", "http://image3_product.png"));
        return product;
    }

    @Test
    void test_delete_product_and_found() throws Exception {
        // Given
        doNothing().when(productService).deleteProduct(anyString());

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/products/50")
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    void test_delete_product_and_not_found() throws Exception {
        // Given
        doThrow(new ProductNotFoundException("50")).when(productService).deleteProduct("50");

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/products/50")
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Could not find product 50"));

    }


    @Test
    void updateProduct() {
    }
}