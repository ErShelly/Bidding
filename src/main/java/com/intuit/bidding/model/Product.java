package com.intuit.bidding.model;

import com.intuit.bidding.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name="product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotBlank(message = "Product name cannot be blank")
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @NotNull
    @OneToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private ProductCategory productCategory;

    @NotNull
    @OneToOne
    @JoinColumn(name = "vendorId", referencedColumnName = "id")
    private Vendor vendor;

    @NotNull(message = "Product's base price cannot be blank")
    @Column(name="basePrice")
    private double basePrice;

    @Column(name="status")
    private ProductStatus status;

    @Column(name="created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name="lastModified")
    @UpdateTimestamp
    private LocalDateTime lastModified;

    public Product(String name, String description, ProductCategory productCategory, Vendor vendor, ProductStatus status) {
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
        this.vendor = vendor;
        this.status = status;
    }
}
