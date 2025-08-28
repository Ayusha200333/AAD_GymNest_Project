package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    boolean existsByName(String name);
    Optional<Location> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);

}
