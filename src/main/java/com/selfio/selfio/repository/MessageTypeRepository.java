package com.selfio.selfio.repository;

import com.selfio.selfio.entities.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Integer> {
    public MessageType findByMessageType(String messageType);
}
