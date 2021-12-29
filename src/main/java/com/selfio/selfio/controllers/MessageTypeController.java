package com.selfio.selfio.controllers;

import com.selfio.selfio.entities.MessageType;
import com.selfio.selfio.repository.MessageTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/messagetype/")
public class MessageTypeController {
    private final MessageTypeRepository messageTypeRepository;

    public MessageTypeController(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    @GetMapping(value = "get/all")
    public ResponseEntity<List<MessageType>> getAll() {
        return ResponseEntity.ok(messageTypeRepository.findAll());
    }

    @PutMapping(value = "add")
    public ResponseEntity<String> addMessagetype (@RequestParam("messagetype") String newType) {
        MessageType messageType = new MessageType();
        messageType.setMessageType(newType);
        messageTypeRepository.save(messageType);
        return ResponseEntity.ok("added");
    }
}
