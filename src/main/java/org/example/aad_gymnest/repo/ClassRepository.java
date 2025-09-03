package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long>{
        Optional<ClassEntity> findByName(String name);
}


