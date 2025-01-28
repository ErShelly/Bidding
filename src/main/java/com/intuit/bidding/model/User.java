package com.intuit.bidding.model;

import com.intuit.bidding.enums.ProductStatus;
import com.intuit.bidding.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name="user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="username")
    private String username;

    @Column(name="emailId")
    private String emailId;

    @Column(name="status")
    private UserStatus status;

    @Column(name="created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name="lastModified")
    @UpdateTimestamp
    private LocalDateTime lastModified;

    public User(String username, String emailId, UserStatus status) {
        this.username = username;
        this.emailId = emailId;
        this.status = status;
    }
}
