package com.intuit.bidding.service;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.enums.BidStatus;
import com.intuit.bidding.exception.*;
import com.intuit.bidding.model.Bid;
import com.intuit.bidding.model.Product;
import com.intuit.bidding.repository.AuctionRepository;
import com.intuit.bidding.repository.BidRepository;
import com.intuit.bidding.repository.ProductRepository;
import com.intuit.bidding.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    private final AuctionService auctionService;

    private final UserService userService;

    private final ProductService productService;

    private final BidRepository bidRepository;

    public BidService(AuctionService auctionService, UserService userService, ProductService productService, BidRepository bidRepository) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.productService = productService;
        this.bidRepository = bidRepository;
    }

    public List<Bid> findBy(long productId) throws Exception {
        Product product = productService.findBy(productId);

        Optional<List<Bid>> bids = bidRepository.findByProduct(product);
        return bids.orElseGet(ArrayList::new);
    }

    public Bid updateBid(long id, double bidAmount) throws Exception {
        Optional<Bid> bid = bidRepository.findById(id);
        if (!bid.isPresent()) throw new InvalidBidException(ErrorMessages.BID_NOT_FOUND);

        validate(bid.get());
        bid.get().setBidAmount(bidAmount);
        return bidRepository.save(bid.get());
    }

    public Bid updateBid(Bid bid) throws Exception {
        validate(bid);
        return bidRepository.save(bid);
    }

    public Bid createBid(Bid bid) throws Exception {
        validate(bid);
        Optional<Bid> bidExists = bidRepository.findByAuctionAndUserAndProduct(bid.getAuction(), bid.getUser(), bid.getProduct());

        if (bidExists.isPresent()) {
            if (bidExists.get().getStatus().equals(BidStatus.CANCELLED)) {
                bidExists.get().setBidAmount(bid.getBidAmount());
                return bidRepository.save(bidExists.get());
            } else {
                throw new InvalidBidException(ErrorMessages.BID_ALREADY_EXISTS);
            }
        }

        bid.setStatus(BidStatus.PLACED);
        return bidRepository.save(bid);
    }

    private void validate(Bid bid) throws Exception {
        if(bid.getBidAmount() < bid.getProduct().getBasePrice()) throw new InvalidBidException(ErrorMessages.INVALID_BID);
    }
}
