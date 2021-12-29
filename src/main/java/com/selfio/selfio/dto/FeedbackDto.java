package com.selfio.selfio.dto;

import lombok.Data;

/**
 * The class describes the content of the feedback request.
 */
@Data
public class FeedbackDto {
    private String email;
    private String feedbackText;
    private String messageType;
}
