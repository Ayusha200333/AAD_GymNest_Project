package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.GuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
    boolean existsByEmail(String email);
    Optional<GuideEntity> findByEmail(String email);
    List<GuideEntity> findAllByBookedAndStatus(String booked, String status);
}
