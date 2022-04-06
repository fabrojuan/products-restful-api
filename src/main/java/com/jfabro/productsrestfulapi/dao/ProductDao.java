package com.jfabro.productsrestfulapi.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductDao {
    @Id
    @Column(name = "sku")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sku;
    @Column(name = "name")
    private String name;
    @Column(name = "brand")
    private String brand;
    @Column(name = "size")
    private String size;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "principal_image")
    private String principalImageUrl;

    @JoinColumn(name = "sku")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageDao> otherImagesUrl;
}
