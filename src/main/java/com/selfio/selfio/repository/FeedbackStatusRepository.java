package com.selfio.selfio.repository;

import com.selfio.selfio.entities.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface is a Spring Repository for relation 'feedback_status'.
 */
@Repository
public interface FeedbackStatusRepository extends JpaRepository<FeedbackStatus, Integer> {
    FeedbackStatus findByStatusName(String statusName);
}
