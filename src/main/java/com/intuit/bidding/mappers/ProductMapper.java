package com.intuit.bidding.mappers;

import com.intuit.bidding.model.Product;
import com.intuit.bidding.model.ProductCategory;
import generated.bidding.entities.types.Category;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    List<Product> mapToModel(List<generated.bidding.entities.types.Product> products);

    Product mapToModel(generated.bidding.entities.types.Product product);

    Product mapToModel(generated.bidding.entities.types.CreateProductInput createProductInput);

    @Mapping(target="category", source = "productCategory")
    List<generated.bidding.entities.types.Product> mapToEntity(List<Product> products);

    @Mapping(target="category", source = "productCategory")
    generated.bidding.entities.types.Product mapToEntity(Product product);

    List<Category> mapCategory(List<ProductCategory> product);
}
