package com.intuit.bidding.model;

import com.intuit.bidding.enums.NotificationStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="auction")
@Builder
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "bidId", referencedColumnName = "id")
    private Bid bid;

    @Column(name="status")
    private NotificationStatus notificationStatus;
}

