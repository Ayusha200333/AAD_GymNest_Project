package org.example.aad_gymnest.controller;
import org.example.aad_gymnest.dto.UserReviewDTO;
import org.example.aad_gymnest.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-reviews")
@CrossOrigin(origins = "*")
public class UserReviewController {

    @Autowired
    private UserReviewService userReviewService;

    @PostMapping("/save")
    public ResponseEntity<UserReviewDTO> createReview(@RequestBody UserReviewDTO dto) {
        return ResponseEntity.ok(userReviewService.saveReview(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(userReviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReviewDTO> getReviewById(@PathVariable Integer id) {
        return ResponseEntity.ok(userReviewService.getReviewById(id));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<UserReviewDTO>> getReviewsByUser(@PathVariable String email) {
        return ResponseEntity.ok(userReviewService.getReviewsByUserEmail(email));
    }

    @PutMapping("/update")
    public ResponseEntity<UserReviewDTO> updateReview(@RequestBody UserReviewDTO dto) {
        return ResponseEntity.ok(userReviewService.updateReview(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        userReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}