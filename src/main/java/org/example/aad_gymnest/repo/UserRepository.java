package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String username);
    boolean existsByEmail(String username);
}
