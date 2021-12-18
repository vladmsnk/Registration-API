package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.FeedbackDto;
import com.selfio.selfio.entities.FeedbackStatus;
import com.selfio.selfio.entities.MessageType;
import com.selfio.selfio.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedback-status")
    public ResponseEntity<List<MessageType>> getFeedbackStatusTypes() {
        return ResponseEntity.ok().body(feedbackService.getStatuses());
    }

    @PostMapping
    public ResponseEntity<String> addFeedback(@RequestBody FeedbackDto feedbackDto){
        try {
            feedbackService.saveFeedback(feedbackDto.getMessageType(),
                    feedbackDto.getFeedbackText(), feedbackDto.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong values");
        }
    }
}
