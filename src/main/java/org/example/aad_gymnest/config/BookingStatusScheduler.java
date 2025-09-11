package org.example.aad_gymnest.config;

import org.example.aad_gymnest.entity.BookingEntity;
import org.example.aad_gymnest.entity.GuideEntity;
import org.example.aad_gymnest.repo.BookingRepository;
import org.example.aad_gymnest.repo.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingStatusScheduler {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuideRepository guideRepository;

    // Run every day at midnight (adjust cron as needed)
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateBookingStatus() {
        LocalDate today = LocalDate.now();

        // Get all bookings where endDate is today or earlier and status is ACTIVE
        List<BookingEntity> completedBookings = bookingRepository.findByEndDateLessThanEqualAndStatus(today, "ACTIVE");

        for (BookingEntity booking : completedBookings) {
            GuideEntity coach = booking.getCoach();

            if (coach != null) {
                coach.setBooked("NO"); // Free the coach
                guideRepository.save(coach);
            }

            booking.setStatus("COMPLETED"); // Mark booking as completed
            bookingRepository.save(booking);
        }
    }}
