package com.example.app.micro.productservice.infrastructure.persistence.mapper;


import org.mapstruct.Mapper;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    List<Product> toDomainList(List<ProductEntity> entities);
}
