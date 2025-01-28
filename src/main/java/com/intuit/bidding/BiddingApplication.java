package com.intuit.bidding;

import com.intuit.bidding.enums.AuctionStatus;
import com.intuit.bidding.enums.ProductStatus;
import com.intuit.bidding.enums.UserStatus;
import com.intuit.bidding.enums.VendorStatus;

import com.intuit.bidding.model.*;

import com.intuit.bidding.repository.*;
import com.intuit.bidding.service.ConcludeAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BiddingApplication {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(BiddingApplication.class, args);
	}

	@Scheduled(cron="*/5 * * * * *") //Every 5 second for demo purpose
	void concludeAuction() throws Exception {
		ConcludeAuctionService concludeAuctionService = context.getBean(ConcludeAuctionService.class);

		concludeAuctionService.concludeWinner();
		Thread.sleep(1000L);
	}

	/*@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository, VendorRepository vendorRepository, ProductRepository productRepository,
										ProductCategoryRepository productCategoryRepository, AuctionRepository auctionRepository) {
		return args -> {
			User john = userRepository.save(new User("john", "john@gmail.com", UserStatus.ACTIVE));
			User mark = userRepository.save(new User("mark", "mark@gmail.com", UserStatus.ACTIVE));

			Vendor keith = vendorRepository.save(new Vendor("keith", "keith@email.com", VendorStatus.ACTIVE));
			Vendor charles = vendorRepository.save(new Vendor("charles", "charles@email.com", VendorStatus.ACTIVE));

			ProductCategory appliance = productCategoryRepository.save(new ProductCategory("APPLIANCE", "Appliance"));
			ProductCategory electronics = productCategoryRepository.save(new ProductCategory("ELECTRONICS", "Electronics"));

			List<Vendor> vendors = new ArrayList<>();
			vendors.add(keith);
			vendors.add(charles);

			productRepository.saveAll(List.of(
					new Product("TV", "Samsung 24 inch", appliance, keith, ProductStatus.UNSOLD),
					new Product("Books", "Be here now", electronics, charles, ProductStatus.UNSOLD)
			));
		};
	}*/
}
