package com.intuit.bidding.model;

import com.intuit.bidding.enums.BidStatus;
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
@Table(name="bid")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "auctionId", referencedColumnName = "id")
    private Auction auction;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    @NotNull(message = "Bid cannot be blank")
    @Column(name="bidAmount")
    private double bidAmount;

    @Column(name="status")
    private BidStatus status;

    @Column(name="created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name="lastModified")
    @UpdateTimestamp
    private LocalDateTime lastModified;

    public Bid(Auction auction, User user, Product product, double bidAmount, BidStatus status) {
        this.auction = auction;
        this.user = user;
        this.product = product;
        this.bidAmount = bidAmount;
        this.status = status;
    }
}

