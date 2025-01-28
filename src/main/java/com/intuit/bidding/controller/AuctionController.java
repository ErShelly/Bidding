package com.intuit.bidding.controller;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.mappers.AuctionMapper;
import com.intuit.bidding.service.AuctionService;
import com.intuit.bidding.service.ProductService;
import com.intuit.bidding.service.UserService;
import generated.bidding.entities.types.Auction;
import generated.bidding.entities.types.CreateAuctionInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class AuctionController {
    private final AuctionService auctionService;
    private final UserService userService;

    private final ProductService productService;

    public AuctionController(AuctionService auctionService, UserService userService, ProductService productService) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.productService = productService;
    }

    @QueryMapping
    List<generated.bidding.entities.types.Auction> auctionsByTime(@Argument OffsetDateTime startTime, @Argument OffsetDateTime endTime) throws Exception {
        com.intuit.bidding.model.Auction auction1 = auctionService.findBy(1);
        List<com.intuit.bidding.model.Auction> auctions = auctionService.findBy(startTime, endTime);
        return AuctionMapper.INSTANCE.map(auctions);
    }

    @MutationMapping
    Auction createAuction(@Argument(name="auction") CreateAuctionInput createAuctionInput) throws Exception {
        com.intuit.bidding.model.Auction auctionToBeCreated = AuctionMapper.INSTANCE.mapAuction(createAuctionInput);
        auctionToBeCreated.setStatus(AuctionStatus.SCHEDULED);

        return AuctionMapper.INSTANCE.mapAuction(auctionService.createAuction(auctionToBeCreated));
    }
}
