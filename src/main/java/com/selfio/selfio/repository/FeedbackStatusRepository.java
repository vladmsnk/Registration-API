package com.selfio.selfio.repository;

import com.selfio.selfio.entities.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackStatusRepository extends JpaRepository<FeedbackStatus, Integer> {
    public FeedbackStatus findByStatusName(String statusName);
}
