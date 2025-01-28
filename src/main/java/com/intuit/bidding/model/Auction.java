package com.intuit.bidding.model;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="auction")
@Builder
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @OneToMany
    @JoinColumn(name = "vendorId", referencedColumnName = "id")
    private List<Vendor> vendors;

    @OneToMany
    @JoinColumn(name = "auctionId", referencedColumnName = "id")
    private List<Product> products;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "bidId", referencedColumnName = "id")
    private List<Bid> bids;

    @Column(name="status")
    private AuctionStatus status;

    @Column(name="startTime")
    private OffsetDateTime startTime;

    @Column(name="endTime")
    private OffsetDateTime endTime;

    @Column(name="created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name="lastModified")
    @UpdateTimestamp
    private LocalDateTime lastModified;

    public Auction(AuctionStatus status, OffsetDateTime startTime, OffsetDateTime endTime) {
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

