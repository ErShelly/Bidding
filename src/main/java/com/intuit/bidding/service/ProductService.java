package com.intuit.bidding.service;

import com.intuit.bidding.enums.ProductStatus;
import com.intuit.bidding.exception.*;
import com.intuit.bidding.model.Auction;
import com.intuit.bidding.model.Product;
import com.intuit.bidding.model.ProductCategory;
import com.intuit.bidding.model.Vendor;
import com.intuit.bidding.repository.ProductCategoryRepository;
import com.intuit.bidding.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final AuctionService auctionService;

    private final VendorService vendorService;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, AuctionService auctionService, VendorService vendorService) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.auctionService = auctionService;
        this.vendorService = vendorService;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product findBy(long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND);
        return product.get();
    }

    public List<Product> findBy(String auctionId, List<String> categoryIds) throws Exception {
        Auction auction = auctionService.findBy(Long.parseLong(auctionId));

        List<Product> matchingProducts = auction.getProducts().stream()
                .filter(product -> categoryIds.stream().anyMatch(id -> product.getProductCategory().getId() == Long.parseLong(id)))
                .collect(Collectors.toList());

        return matchingProducts;
    }

    public Product createProduct(Product product) throws Exception {
        Vendor vendor = vendorService.findById(product.getVendor().getId());
        product.setStatus(ProductStatus.UNSOLD);

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public List<ProductCategory> findAll(){
        return productCategoryRepository.findAll();
    }

    public ProductCategory findCategoryById(long id) throws Exception {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);

        if (!productCategory.isPresent()) throw new InvalidCategoryException(ErrorMessages.CATEGORY_NOT_FOUND);
        return productCategory.get();
    }
}
