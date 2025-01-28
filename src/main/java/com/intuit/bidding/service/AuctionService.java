package com.intuit.bidding.service;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.exception.ErrorMessages;
import com.intuit.bidding.exception.AuctionException;
import com.intuit.bidding.model.Auction;
import com.intuit.bidding.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public Auction createAuction(Auction auction) throws Exception {
        return auctionRepository.save(auction);
    }

    public List<Auction> findBy(OffsetDateTime auctionStart, OffsetDateTime auctionEnd){
        Optional<List<Auction>> auctions = auctionRepository.findByStartTimeAndEndTime(auctionStart, auctionEnd);
        return auctions.isPresent() ? auctions.get() :  new ArrayList<>();
    }

    public Auction findBy(long id) throws AuctionException {
        Optional<Auction> auction = auctionRepository.findById(id);

        if (!auction.isPresent()) throw new AuctionException(ErrorMessages.AUCTION_NOT_FOUND);
        return auction.get();
    }

    public List<Auction> findBy(AuctionStatus status){
        Optional<List<Auction>> auctions = auctionRepository.findByStatus(status);
        return auctions.isPresent() ? auctions.get() :  new ArrayList<>();
    }

    public Auction updateStatus(Auction auction) {
        return auctionRepository.save(auction);
    }
}
