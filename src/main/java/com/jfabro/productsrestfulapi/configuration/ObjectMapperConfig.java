package com.jfabro.productsrestfulapi.configuration;

import com.jfabro.productsrestfulapi.dao.ProductDao;
import com.jfabro.productsrestfulapi.dto.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(ProductDto.class, ProductDao.class)
                .addMappings(mapper -> mapper.skip(ProductDao::setOtherImagesUrl));
        return modelMapper;
    }
}
