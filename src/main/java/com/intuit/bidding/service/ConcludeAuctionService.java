package com.intuit.bidding.service;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.enums.BidStatus;
import com.intuit.bidding.enums.ProductStatus;
import com.intuit.bidding.model.Auction;
import com.intuit.bidding.model.Bid;
import com.intuit.bidding.model.Product;
import com.intuit.bidding.repository.NotificationJobRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcludeAuctionService {
    private final NotificationJobRepository notificationJobRepository;

    private final ProductService productService;
    private final BidService bidService;

    private final AuctionService auctionService;

    public ConcludeAuctionService(NotificationJobRepository notificationJobRepository, ProductService productService, BidService bidService, AuctionService auctionService) {
        this.notificationJobRepository = notificationJobRepository;
        this.productService = productService;
        this.bidService = bidService;
        this.auctionService = auctionService;
    }

    public void concludeWinner() throws Exception {
        List<Auction> inProgressAuctions = auctionService.findBy(AuctionStatus.IN_PROGRESS);

        System.out.println("Background job running");
        if (inProgressAuctions.size() > 0) {
            for (Auction auction : inProgressAuctions) {
                System.out.println("Auction Ends at: " + auction.getEndTime());
                if (auction.getEndTime().isBefore(OffsetDateTime.now())) {

                    List<Bid> bids = getWinningBidsByProduct(auction.getBids());
                    for (Bid bid: bids) {
                        bid.setStatus(BidStatus.WON);
                        bidService.updateBid(bid);

                        System.out.println("****** " + bid.getUser().getUsername() + " wins the bid for product " +
                                bid.getProduct().getName());

                        Product soldProduct = bid.getProduct();
                        soldProduct.setStatus(ProductStatus.SOLD);
                        productService.updateProduct(soldProduct);
                    }

                    auction.setStatus(AuctionStatus.ENDED);
                    auctionService.updateStatus(auction);
                }
            }
        }
    }

    public List<Bid> getWinningBidsByProduct(List<Bid> bids) {
        return new ArrayList<>(bids.stream()
                .filter(bid -> bid.getStatus() != BidStatus.CANCELLED)
                .collect(Collectors.groupingBy(Bid::getProduct,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Bid::getBidAmount)),
                                Optional::get)))
                .values());
    }
}
