package com.intuit.bidding.service;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.enums.ProductStatus;
import com.intuit.bidding.exception.InvalidBidException;
import com.intuit.bidding.model.*;
import com.intuit.bidding.repository.AuctionRepository;
import com.intuit.bidding.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuctionServiceTest {
    @MockBean
    AuctionRepository auctionRepository;

    @MockBean
    BidRepository bidRepository;

    @Autowired
    AuctionService auctionService;

    @Autowired
    BidService bidService;

    @Test
    void shouldCreateAuction() throws Exception {
        Auction auctionToBeCreated = new Auction(AuctionStatus.SCHEDULED,
                OffsetDateTime.now(), OffsetDateTime.now());
        auctionToBeCreated.setId(1L);
        when(auctionRepository.save(any(Auction.class))).thenReturn(auctionToBeCreated);

        Auction auctionCreated = auctionService.createAuction(auctionToBeCreated);

        assertEquals(auctionCreated.getId(), auctionToBeCreated.getId());
        verify(auctionRepository, times(1)).save(any(Auction.class));
    }

    @Test
    void shouldUpdateAuction() throws Exception {
        Auction auctionToBeUpdated = new Auction(AuctionStatus.SCHEDULED,
                OffsetDateTime.now(), OffsetDateTime.now());
        auctionToBeUpdated.setId(1L);
        when(auctionRepository.save(any(Auction.class))).thenReturn(auctionToBeUpdated);

        Auction auctionUpdated = auctionService.updateStatus(auctionToBeUpdated);

        assertEquals(auctionUpdated.getId(), auctionToBeUpdated.getId());
        verify(auctionRepository, times(1)).save(any(Auction.class));
    }

    @Test
    void shouldUpdateBid() throws Exception {
        Vendor vendor = new Vendor();
        ProductCategory productCategory = new ProductCategory();

        Product product = new Product("ss", "ss", productCategory, vendor, ProductStatus.UNSOLD);
        product.setBasePrice(200d);

        Bid bid = new Bid();
        bid.setId(1L);
        bid.setBidAmount(400d);
        bid.setProduct(product);

        when(bidRepository.findById(anyLong())).thenReturn(Optional.of(bid));
        when(bidRepository.save(any())).thenReturn(bid);

        Bid bidToBeUpdated = bidService.updateBid(1L, 200d);

        assertEquals(bid, bidToBeUpdated);
        verify(bidRepository, times(1)).findById(anyLong());
        verify(bidRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowException_whenBidIdNotValid() throws Exception{
        when(bidRepository.findById(anyLong())).thenReturn(Optional.empty());

        try{
            Bid bidToBeUpdated = bidService.updateBid(1L, 200d);
        }
        catch (Exception ex){
            assertEquals(ex.getClass(), InvalidBidException.class);
        }
    }
}