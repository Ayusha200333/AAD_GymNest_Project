package org.example.aad_gymnest.service.impl;
import org.example.aad_gymnest.dto.UserReviewDTO;
import org.example.aad_gymnest.entity.UserEntity;
import org.example.aad_gymnest.entity.UserReviewEntity;
import org.example.aad_gymnest.repo.UserRepository;
import org.example.aad_gymnest.repo.UserReviewRepository;
import org.example.aad_gymnest.service.UserReviewService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReviewServiceImpl implements UserReviewService {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserReviewDTO saveReview(UserReviewDTO dto) {
        UserEntity user = userRepository.findByEmail(dto.getUserEmail());
        if (user == null) throw new RuntimeException("User not found with email: " + dto.getUserEmail());

        UserReviewEntity review = new UserReviewEntity();
        review.setUser(user);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        UserReviewEntity saved = userReviewRepository.save(review);
        return mapToDTO(saved);
    }

    @Override
    public List<UserReviewDTO> getAllReviews() {
        List<UserReviewEntity> reviews = userReviewRepository.findAll();
        List<UserReviewDTO> dtos = modelMapper.map(reviews, new TypeToken<List<UserReviewDTO>>() {}.getType());

        for (int i = 0; i < reviews.size(); i++) {
            UserReviewEntity r = reviews.get(i);
            UserReviewDTO dto = dtos.get(i);
            dto.setUserEmail(r.getUser().getEmail());
            dto.setUserName(r.getUser().getName());
        }
        return dtos;
    }

    @Override
    public UserReviewDTO getReviewById(Integer id) {
        UserReviewEntity review = userReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id));
        return mapToDTO(review);
    }

    @Override
    public UserReviewDTO updateReview(UserReviewDTO dto) {
        UserReviewEntity review = userReviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + dto.getReviewId()));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        if (!review.getUser().getEmail().equals(dto.getUserEmail())) {
            UserEntity user = userRepository.findByEmail(dto.getUserEmail());
            if (user == null) throw new RuntimeException("User not found with email: " + dto.getUserEmail());
            review.setUser(user);
        }

        UserReviewEntity updated = userReviewRepository.save(review);
        return mapToDTO(updated);
    }

    @Override
    public void deleteReview(Integer id) {
        if (!userReviewRepository.existsById(id)) throw new RuntimeException("Review not found with ID: " + id);
        userReviewRepository.deleteById(id);
    }

    @Override
    public List<UserReviewDTO> getReviewsByUserEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found with email: " + email);

        List<UserReviewEntity> reviews = userReviewRepository.findByUser(user);
        List<UserReviewDTO> dtos = modelMapper.map(reviews, new TypeToken<List<UserReviewDTO>>() {}.getType());

        for (int i = 0; i < reviews.size(); i++) {
            UserReviewEntity r = reviews.get(i);
            UserReviewDTO dto = dtos.get(i);
            dto.setUserEmail(r.getUser().getEmail());
            dto.setUserName(r.getUser().getName());
        }
        return dtos;
    }

    private UserReviewDTO mapToDTO(UserReviewEntity review) {
        UserReviewDTO dto = modelMapper.map(review, UserReviewDTO.class);
        dto.setUserEmail(review.getUser().getEmail());
        dto.setUserName(review.getUser().getName());
        return dto;
    }
}