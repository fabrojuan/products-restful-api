package com.jfabro.productsrestfulapi.service;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dao.ProductImageDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.exceptions.ProductNotFoundException;
import com.jfabro.productsrestfulapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductsDto getAllProducts() {

        ProductsDto productsDto = new ProductsDto();
        productsDto.setProducts(new ArrayList<>());

        repository.findAll().forEach(p -> {
            ProductDto productDto = new ProductDto();
            // mejorar con algun mapper
            productDto.setSku(p.getSku());
            productDto.setName(p.getName());
            productDto.setBrand(p.getBrand());
            productDto.setSize(p.getSize());
            productDto.setPrice(p.getPrice());
            productsDto.getProducts().add(productDto);
        });

        return productsDto;
    }

    @Override
    public ProductDto getProduct(String idProduct) {
        ProductDao productDao = repository.findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));

        // mejorar con algun mapper
        ProductDto productDto = new ProductDto();
        productDto.setSku(productDao.getSku());
        productDto.setName(productDao.getName());
        productDto.setBrand(productDao.getBrand());
        productDto.setSize(productDao.getSize());
        productDto.setPrice(productDao.getPrice());

        return productDto;
    }

    @Override
    public ProductDto saveProduct(ProductDto product) {
        ProductDao productDao = new ProductDao();
        productDao.setSku(product.getSku());
        productDao.setName(product.getName());
        productDao.setBrand(product.getBrand());
        productDao.setSize(product.getSize());
        productDao.setPrice(product.getPrice());
        productDao.setOtherImagesUrl(new ArrayList<ProductImageDao>());

        if (product.getOtherImagesUrl() != null
                && !product.getOtherImagesUrl().isEmpty()) {

            product.getOtherImagesUrl()
                    .stream()
                    .forEach(p -> {
                        ProductImageDao image = new ProductImageDao();
                        image.setImageUrl(p);
                        productDao.getOtherImagesUrl().add(image);
                    });
        }

        repository.save(productDao);

        return product;
    }

    @Override
    public void deleteProduct(String idProduct) {
        ProductDao product = repository
                .findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));
        repository.delete(product);
    }
}
