package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.UserReviewDTO;

import java.util.List;

public interface UserReviewService {
    UserReviewDTO saveReview(UserReviewDTO userReviewDTO);
    List<UserReviewDTO> getAllReviews();
    UserReviewDTO getReviewById(Integer id);
    UserReviewDTO updateReview(UserReviewDTO userReviewDTO);
    void deleteReview(Integer id);
    List<UserReviewDTO> getReviewsByUserEmail(String email);
}