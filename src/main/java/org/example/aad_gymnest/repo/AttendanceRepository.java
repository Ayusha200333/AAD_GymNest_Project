package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.AttendanceEntity;
import org.example.aad_gymnest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {
    Optional<AttendanceEntity> findByMemberAndDate(UserEntity member, LocalDate date);

    List<AttendanceEntity> findByDate(LocalDate date);

    List<AttendanceEntity> findByMember_NameContainingIgnoreCaseOrMember_EmailContainingIgnoreCase(
            String name, String email);

    List<AttendanceEntity> findByDateAndMember_NameContainingIgnoreCaseOrDateAndMember_EmailContainingIgnoreCase(
            LocalDate date1, String name,
            LocalDate date2, String email);
}
