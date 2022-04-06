package com.jfabro.productsrestfulapi.controller;

import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ProductsDto> getAllProducts() {

        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("idProduct") String idProduct) {
        return ResponseEntity.ok().body(productService.getProduct(idProduct));
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductDto product) {
        return ResponseEntity.ok().body(productService.saveProduct(product));
    }

    @DeleteMapping("/{idProduct}")
    public void deleteProduct(@PathVariable("idProduct") String idProduct) {
        productService.deleteProduct(idProduct);
    }

}
