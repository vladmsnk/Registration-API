package com.selfio.selfio.repository;

import com.selfio.selfio.entities.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface is a Spring Repository for relation 'messagetype'.
 */
@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Integer> {
    MessageType findByMessageType(String messageType);
}
