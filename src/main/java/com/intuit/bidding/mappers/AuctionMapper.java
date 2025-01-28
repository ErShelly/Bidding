package com.intuit.bidding.mappers;

import com.intuit.bidding.model.Auction;
import generated.bidding.entities.types.CreateAuctionInput;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuctionMapper {
    AuctionMapper INSTANCE = Mappers.getMapper( AuctionMapper.class );

    Auction mapAuction(CreateAuctionInput createAuctionInput);

    generated.bidding.entities.types.Auction mapAuction(Auction auction);

    List<generated.bidding.entities.types.Auction> map(List<Auction> auctions);

}
