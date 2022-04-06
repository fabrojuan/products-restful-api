package com.jfabro.productsrestfulapi.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDto {
    private String sku;
    @NotBlank(message = "Product name must not be blank")
    @Size(min = 3, max = 50, message = "Min size for product name must be 3 and Max size must be 50")
    private String name;
    @NotBlank(message = "Product brand must not be blank")
    @Size(min = 3, max = 50, message = "Min size for product size must be 3 and Max size must be 50")
    private String brand;
    @NotBlank(message = "Product size must not be blank")
    private String size;
    @Min(value = 1, message = "Min value for product price must be 1.0")
    @Max(value = 99999999, message = "Max value for product price must be 99999999.0")
    private BigDecimal price;
    @NotBlank(message = "Product principal image must not be blank")
    @URL(message = "Principal Image is not a valid URL")
    private String principalImageUrl;
    private List<String> otherImagesUrl;
}
