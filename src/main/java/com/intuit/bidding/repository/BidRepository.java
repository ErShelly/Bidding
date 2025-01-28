package com.intuit.bidding.repository;

import com.intuit.bidding.model.Auction;
import com.intuit.bidding.model.Bid;
import com.intuit.bidding.model.Product;
import com.intuit.bidding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findByAuctionAndUserAndProduct(Auction auction, User user, Product product);
    Optional<List<Bid>> findByProduct(Product product);
}
