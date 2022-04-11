package com.jfabro.productsrestfulapi.controller;

import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductsDto getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("idProduct") String idProduct) {
        return productService.getProduct(idProduct);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto saveProduct(@Valid @RequestBody ProductDto product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("idProduct") String idProduct) {
        productService.deleteProduct(idProduct);
    }

    @PutMapping("/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable("idProduct") String idProduct,
                                    @Valid @RequestBody ProductDto product) {
        return productService.updateProduct(idProduct, product);
    }

}
