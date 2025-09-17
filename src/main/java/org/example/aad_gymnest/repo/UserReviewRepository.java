package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.UserEntity;
import org.example.aad_gymnest.entity.UserReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReviewEntity, Integer> {
    List<UserReviewEntity> findByUser(UserEntity user);
}