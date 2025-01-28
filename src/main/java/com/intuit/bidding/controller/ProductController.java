package com.intuit.bidding.controller;

import com.intuit.bidding.mappers.ProductMapper;
import com.intuit.bidding.model.ProductCategory;
import com.intuit.bidding.model.Vendor;
import com.intuit.bidding.service.ProductService;
import com.intuit.bidding.service.VendorService;
import generated.bidding.entities.types.Category;
import generated.bidding.entities.types.CreateProductInput;
import generated.bidding.entities.types.Product;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final VendorService vendorService;

    public ProductController(ProductService productService, VendorService vendorService) {
        this.productService = productService;
        this.vendorService = vendorService;
    }

    @QueryMapping
    List<Product> products(@Argument String auctionId, @Argument List<String> categoryIds) throws Exception {
        List<com.intuit.bidding.model.Product> products = productService.findBy(auctionId, categoryIds);
        return ProductMapper.INSTANCE.mapToEntity(products);
    }

    @QueryMapping
    List<Category> categories() {
        List<com.intuit.bidding.model.ProductCategory> categories = productService.findAll();
        return ProductMapper.INSTANCE.mapCategory(categories);

    }

    @MutationMapping
    Product createProduct(@Valid @Argument(name="product") CreateProductInput createProductInput) throws Exception {
        com.intuit.bidding.model.Product productToBeCreated = ProductMapper.INSTANCE.mapToModel(createProductInput);

        Vendor vendor = vendorService.findById(Long.parseLong(createProductInput.getVendorId()));
        productToBeCreated.setVendor(vendor);

        ProductCategory productCategory = productService.findCategoryById(Long.parseLong(createProductInput.getCategoryId()));
        productToBeCreated.setProductCategory(productCategory);

        return ProductMapper.INSTANCE.mapToEntity(productService.createProduct(productToBeCreated));
    }
}
