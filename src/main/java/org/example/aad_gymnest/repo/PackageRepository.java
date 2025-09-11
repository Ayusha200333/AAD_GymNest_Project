package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
boolean existsByName(String name);

Optional<PackageEntity> findByName(String name);

    Optional<PackageEntity> findByNameIgnoreCase(String name);
}
