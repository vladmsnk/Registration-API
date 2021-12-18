package com.selfio.selfio.dto;

import lombok.Data;

@Data
public class FeedbackDto {
    private String email;
    private String feedbackText;
    private String messageType;
}
