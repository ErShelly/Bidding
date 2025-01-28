package com.intuit.bidding.repository;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Optional<List<Auction>> findByStartTimeAndEndTime(OffsetDateTime startTime, OffsetDateTime endTime);
    Optional<List<Auction>> findByStatus(AuctionStatus status);
}
