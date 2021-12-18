package com.selfio.selfio.service;

import com.selfio.selfio.entities.Feedback;
import com.selfio.selfio.entities.FeedbackStatus;
import com.selfio.selfio.entities.MessageType;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.FeedbackRepository;
import com.selfio.selfio.repository.MessageTypeRepository;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final MessageTypeRepository messageTypeRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           UserRepository userRepository,
                           MessageTypeRepository messageTypeRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.messageTypeRepository = messageTypeRepository;
    }

    public void saveFeedback(String messageType, String text, String email)
            throws IllegalArgumentException{
        User user = this.findUser(email);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        Feedback feedback = new Feedback();
        feedback.setEmail(email);
        feedback.setUserId(user.getId());
        feedback.setMessageType(messageTypeRepository.findByMessageType(messageType).getId());
        feedback.setText(text);
        feedback.setStatusCode(1);
        feedbackRepository.save(feedback);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<MessageType> getStatuses() {
        return messageTypeRepository.findAll();
    }
}
