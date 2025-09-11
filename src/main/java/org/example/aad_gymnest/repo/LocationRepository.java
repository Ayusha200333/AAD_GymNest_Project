package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, String> {
    boolean existsByName(String name);
    Optional<LocationEntity> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);

    Optional<LocationEntity> findByName(String name);
    // Case-insensitive search
    Optional<LocationEntity> findByNameIgnoreCase(String name);
}
