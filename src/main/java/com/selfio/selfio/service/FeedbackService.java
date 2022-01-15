package com.selfio.selfio.service;

import com.selfio.selfio.dto.FeedbackDto;
import com.selfio.selfio.entities.Feedback;
import com.selfio.selfio.entities.FeedbackStatus;
import com.selfio.selfio.entities.MessageType;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.FeedbackRepository;
import com.selfio.selfio.repository.FeedbackStatusRepository;
import com.selfio.selfio.repository.MessageTypeRepository;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final MessageTypeRepository messageTypeRepository;
    private final FeedbackStatusRepository feedbackStatusRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository,
                           UserRepository userRepository,
                           MessageTypeRepository messageTypeRepository,
                           FeedbackStatusRepository feedbackStatusRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.messageTypeRepository = messageTypeRepository;
        this.feedbackStatusRepository = feedbackStatusRepository;
    }

    public void saveFeedback(FeedbackDto feedbackDto)
            throws IllegalArgumentException{
        User user = this.findUser(feedbackDto.getEmail());
        if (user == null) {
            throw new IllegalArgumentException();
        }
        Feedback feedback = new Feedback();
        feedback.setEmail(feedbackDto.getEmail());
        feedback.setUserId(user.getId());
        feedback.setMessageType(messageTypeRepository.findByMessageType(feedbackDto.getMessageType()).getId());
        if (feedback.getMessageType() == null) {
            throw new IllegalArgumentException();
        }
        feedback.setText(feedbackDto.getFeedbackText());
        feedback.setStatusCode(feedbackStatusRepository.findByStatusName("In processing").getId());
        feedbackRepository.save(feedback);
    }

    public void saveFeedbackStatus(String newFeedbackStatus) {
        FeedbackStatus feedbackStatus = new FeedbackStatus();
        feedbackStatus.setStatusName(newFeedbackStatus);
        feedbackStatusRepository.save(feedbackStatus);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<MessageType> getStatuses() {
        return messageTypeRepository.findAll();
    }
}
