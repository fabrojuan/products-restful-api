package com.jfabro.productsrestfulapi.repository;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dao.ProductImageDao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductImageRepository extends CrudRepository<ProductImageDao, Long> {
    Iterable<ProductImageDao> findBySku(String sku);

}
