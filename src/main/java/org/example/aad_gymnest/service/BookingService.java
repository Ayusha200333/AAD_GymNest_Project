package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.BookingDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BookingService {
    @Transactional
    boolean saveBooking(BookingDTO dto);

    int getTotalBookings();

    List<BookingDTO> getAllBookings();

    List<BookingDTO> getBookingsByUserEmail(String email);

    List<Map<String, Object>> getBookingsPerDay();

    List<Map<String, Object>> getTotalPricePerBooking();



    int getTodayBookingCount();
}
