package org.example.aad_gymnest.repo;

import org.example.aad_gymnest.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    // Find expired/completed bookings by endDate and status
    List<BookingEntity> findByEndDateLessThanEqualAndStatus(LocalDate endDate, String status);

    // Find all bookings by user email
    List<BookingEntity> findByUser_Email(String email);

    // Count bookings per day (for statistics / dashboard)
    @Query(value = "SELECT booking_date AS date, COUNT(*) AS count FROM bookings GROUP BY booking_date", nativeQuery = true)
    List<Map<String, Object>> findBookingsPerDay();

    // Total revenue per booking (useful for reports)
    @Query(value = "SELECT id AS bookingId, total_price AS total FROM bookings", nativeQuery = true)
    List<Map<String, Object>> findTotalPricePerBooking();

    // Optional: Find active bookings
    List<BookingEntity> findByStatus(String status);
}