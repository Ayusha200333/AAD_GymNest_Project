package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
boolean existsByName(String name);

Optional<Membership> findByName(String name);
}
