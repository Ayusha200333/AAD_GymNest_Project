package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReviewDTO {
    private Integer reviewId;
    private String userEmail; // Logged-in user email
    private String userName;
    private int rating;  // 1-5
    private String comment;
    private LocalDateTime createdAt;
}