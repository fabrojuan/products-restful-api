package com.jfabro.productsrestfulapi.service;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dao.ProductImageDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.exceptions.ProductNotFoundException;
import com.jfabro.productsrestfulapi.repository.ProductImageRepository;
import com.jfabro.productsrestfulapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductImageRepository imageRepository;

    public ProductServiceImpl(ProductRepository repository,
                              ProductImageRepository productImageRepository) {
        this.productRepository = repository;
        this.imageRepository = productImageRepository;
    }

    @Override
    public ProductsDto getAllProducts() {

        ProductsDto productsDto = new ProductsDto();
        productsDto.setProducts(new ArrayList<>());

        productRepository.findAll().forEach(p -> {
            ProductDto productDto = new ProductDto();
            // mejorar con algun mapper
            productDto.setSku(p.getSku());
            productDto.setName(p.getName());
            productDto.setBrand(p.getBrand());
            productDto.setSize(p.getSize());
            productDto.setPrice(p.getPrice());

            List<String> otherImageUrl = new ArrayList<>();
            imageRepository.findBySku(p.getSku())
                    .iterator().forEachRemaining(image -> otherImageUrl.add(image.getImageUrl()));
            productDto.setOtherImagesUrl(otherImageUrl);

            productsDto.getProducts().add(productDto);
        });

        return productsDto;
    }

    @Override
    public ProductDto getProduct(String idProduct) {
        ProductDao productDao = productRepository.findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));

        // mejorar con algun mapper
        ProductDto productDto = new ProductDto();
        productDto.setSku(productDao.getSku());
        productDto.setName(productDao.getName());
        productDto.setBrand(productDao.getBrand());
        productDto.setSize(productDao.getSize());
        productDto.setPrice(productDao.getPrice());

        List<String> otherImageUrl = new ArrayList<>();
        imageRepository.findBySku(idProduct)
                .iterator().forEachRemaining(image -> otherImageUrl.add(image.getImageUrl()));
        productDto.setOtherImagesUrl(otherImageUrl);

        return productDto;
    }

    @Override
    public ProductDto saveProduct(ProductDto product) {
        ProductDao productDao = new ProductDao();
        productDao.setName(product.getName());
        productDao.setBrand(product.getBrand());
        productDao.setSize(product.getSize());
        productDao.setPrice(product.getPrice());
        productDao.setPrincipalImageUrl(product.getPrincipalImageUrl());
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

        ProductDao savedProduct = productRepository.save(productDao);
        product.setSku(savedProduct.getSku());

        return product;
    }

    @Override
    public ProductDto updateProduct(String idProduct, ProductDto product) {
        ProductDao productDao = productRepository.findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));

        productDao.setName(product.getName());
        productDao.setBrand(product.getBrand());
        productDao.setSize(product.getSize());
        productDao.setPrice(product.getPrice());
        productDao.setPrincipalImageUrl(product.getPrincipalImageUrl());

        ProductDao savedProduct = productRepository.save(productDao);
        product.setSku(savedProduct.getSku());
        return product;
    }

    @Override
    public void deleteProduct(String idProduct) {
        ProductDao product = productRepository
                .findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));
        productRepository.delete(product);
    }
}
