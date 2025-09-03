package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
boolean existsByName(String name);

Optional<MembershipEntity> findByName(String name);
}
