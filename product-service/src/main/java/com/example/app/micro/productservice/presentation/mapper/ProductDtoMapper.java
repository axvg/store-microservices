package com.example.app.micro.productservice.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.infrastructure.client.mapper.UserDtoMapper;
import com.example.app.micro.productservice.presentation.dto.CreateProductRequest;
import com.example.app.micro.productservice.presentation.dto.ProductResponse;
import com.example.app.micro.productservice.presentation.dto.UpdateProductRequest;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class})
public interface ProductDtoMapper {
    Product toDomain(CreateProductRequest request);

    Product toDomain(UpdateProductRequest request);

    @Mapping(target = "available", expression = "java(product.isAvailable())")
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);
}
