package com.jfabro.productsrestfulapi.service;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dao.ProductImageDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import com.jfabro.productsrestfulapi.dto.ProductsDto;
import com.jfabro.productsrestfulapi.exceptions.ProductNotFoundException;
import com.jfabro.productsrestfulapi.repository.ProductImageRepository;
import com.jfabro.productsrestfulapi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductImageRepository imageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = productImageRepository;
    }

    @Override
    public ProductsDto getAllProducts() {

        ProductsDto productsDto = new ProductsDto();
        productsDto.setProducts(new ArrayList<>());

        productRepository.findAll().forEach(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);

            List<String> otherImageUrl = new ArrayList<>();
            imageRepository.findBySku(product.getSku())
                    .iterator()
                    .forEachRemaining(image -> otherImageUrl.add(image.getImageUrl()));
            productDto.setOtherImagesUrl(otherImageUrl);

            productsDto.getProducts().add(productDto);
        });

        return productsDto;
    }

    @Override
    public ProductDto getProduct(String idProduct) {
        ProductDao productDao = productRepository.findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));

        ProductDto productDto = modelMapper.map(productDao, ProductDto.class);

        List<String> otherImageUrls = new ArrayList<>();
        imageRepository.findBySku(idProduct)
                .iterator()
                .forEachRemaining(image -> otherImageUrls.add(image.getImageUrl()));
        productDto.setOtherImagesUrl(otherImageUrls);

        return productDto;
    }

    @Override
    public ProductDto saveProduct(ProductDto product) {
        ProductDao productDao = modelMapper.map(product, ProductDao.class);
        productDao.setOtherImagesUrl(new ArrayList<ProductImageDao>());

        if (!CollectionUtils.isEmpty(product.getOtherImagesUrl())) {

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
