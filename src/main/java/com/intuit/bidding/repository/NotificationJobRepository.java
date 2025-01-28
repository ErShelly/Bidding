package com.intuit.bidding.repository;

import com.intuit.bidding.model.NotificationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationJobRepository extends JpaRepository<NotificationJob, Long> {
}
