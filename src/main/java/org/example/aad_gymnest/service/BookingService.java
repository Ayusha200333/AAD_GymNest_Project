package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.BookingDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BookingService {
    // ===== Save Booking =====
    @Transactional
    boolean saveBooking(BookingDTO dto);

    // ===== Get Total Bookings =====
    int getTotalBookings();

    // ===== Get All Bookings =====
    List<BookingDTO> getAllBookings();

    // ===== Get Bookings by User Email =====
    List<BookingDTO> getBookingsByUserEmail(String email);

    // ===== Dashboard Reports =====
    List<Map<String, Object>> getBookingsPerDay();

    List<Map<String, Object>> getTotalPricePerBooking();



    int getTodayBookingCount();
}
