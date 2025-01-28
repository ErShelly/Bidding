package com.intuit.bidding.mappers;

import com.intuit.bidding.model.Bid;
import generated.bidding.entities.types.CreateBidInput;
import generated.bidding.entities.types.UpdateBidInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BidMapper {
    BidMapper INSTANCE = Mappers.getMapper( BidMapper.class );

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "auctionId", source = "auction.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bidStatus", source = "status")
    generated.bidding.entities.types.Bid map(Bid bid);

    List<generated.bidding.entities.types.Bid> map(List<Bid> bids);

    Bid mapToModel(CreateBidInput bid);
}
