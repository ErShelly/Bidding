package com.intuit.bidding.model;

import com.intuit.bidding.enums.UserStatus;
import com.intuit.bidding.enums.VendorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "vendor")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Vendor name cannot be blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Vendor email address cannot be blank")
    @Column(name = "emailId")
    private String emailId;

    @Column(name = "status")
    private VendorStatus status;

    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "lastModified")
    @UpdateTimestamp
    private LocalDateTime lastModified;

    public Vendor(String name, String emailId, VendorStatus status) {
        this.name = name;
        this.emailId = emailId;
        this.status = status;
    }
}
