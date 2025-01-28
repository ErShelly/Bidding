package com.intuit.bidding.controller;

import com.intuit.bidding.mappers.AuctionMapper;
import com.intuit.bidding.mappers.BidMapper;
import com.intuit.bidding.model.Auction;
import com.intuit.bidding.model.Product;
import com.intuit.bidding.model.User;
import com.intuit.bidding.model.Vendor;
import com.intuit.bidding.service.AuctionService;
import com.intuit.bidding.service.BidService;
import com.intuit.bidding.service.ProductService;
import com.intuit.bidding.service.UserService;
import generated.bidding.entities.types.CreateBidInput;
import generated.bidding.entities.types.UpdateBidInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class BidController {
    private final BidService bidService;

    private final ProductService productService;

    private final AuctionService auctionService;

    private final UserService userService;

    public BidController(BidService bidService, ProductService productService, AuctionService auctionService, UserService userService) {
        this.bidService = bidService;
        this.productService = productService;
        this.auctionService = auctionService;
        this.userService = userService;
    }

    @QueryMapping
    List<generated.bidding.entities.types.Bid> bids(@Argument String productId) throws Exception {
        List<com.intuit.bidding.model.Bid> bids = bidService.findBy(Long.parseLong(productId));
        return BidMapper.INSTANCE.map(bids);
    }

    @MutationMapping
    generated.bidding.entities.types.Bid createBid(@Argument(name="bid") CreateBidInput createBidInput) throws Exception {
        com.intuit.bidding.model.Bid bidToBeCreated = BidMapper.INSTANCE.mapToModel(createBidInput);

        User user = userService.findById(Long.parseLong(createBidInput.getUserId()));
        bidToBeCreated.setUser(user);

        Auction auction = auctionService.findBy(Long.parseLong(createBidInput.getUserId()));
        bidToBeCreated.setAuction(auction);

        Product product = productService.findBy(Long.parseLong(createBidInput.getProductId()));
        bidToBeCreated.setProduct(product);

        com.intuit.bidding.model.Bid bidCreated = bidService.createBid(bidToBeCreated);
        return BidMapper.INSTANCE.map(bidCreated);
    }

    @MutationMapping
    generated.bidding.entities.types.Bid updateBid(@Argument(name="bid") UpdateBidInput updateBidInput) throws Exception {
        com.intuit.bidding.model.Bid bidUpdated = bidService.updateBid(Long.parseLong(updateBidInput.getId()), updateBidInput.getBidAmount().doubleValue());
        return BidMapper.INSTANCE.map(bidUpdated);
    }
}
