package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    boolean existsByEmail(String email);
    Optional<Guide> findByEmail(String email);
    List<Guide> findAllByBookedAndStatus(String booked, String status);
}
