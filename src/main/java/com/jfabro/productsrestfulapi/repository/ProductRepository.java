package com.jfabro.productsrestfulapi.repository;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductDao, String> {
}
