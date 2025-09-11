//package org.example.aad_gymnest.service.impl;
//
//import org.example.aad_gymnest.dto.BookingDTO;
//import org.example.aad_gymnest.entity.*;
//import org.example.aad_gymnest.repo.*;
//import org.example.aad_gymnest.service.BookingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class BookingServiceImpl implements BookingService {
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private GuideRepository guideRepository;
//
//    @Autowired
//    private PackageRepository packageRepository;
//
//    @Autowired
//    private ClassRepository classRepository;
//
//    @Autowired
//    private LocationRepository locationRepository;
//
//    // ===== Save Booking =====
//    @Transactional
//    @Override
//    public boolean saveBooking(BookingDTO dto) {
//        try {
//            PackageEntity gymPackage = packageRepository.findByName(dto.getPackageName())
//                    .orElseThrow(() -> new RuntimeException("Package not found"));
//
//            ClassEntity gymClass = classRepository.findByName(dto.getClassName())
//                    .orElseThrow(() -> new RuntimeException("Class not found"));
//
//            GuideEntity coach = guideRepository.findByEmail(dto.getCoachEmail())
//                    .orElseThrow(() -> new RuntimeException("Coach not found"));
//
//            LocationEntity location = locationRepository.findByName(dto.getLocationName())
//                    .orElseThrow(() -> new RuntimeException("Location not found"));
//
//            UserEntity user = userRepository.findByEmail(dto.getUserEmail());
//
//            BookingEntity booking = new BookingEntity();
//            booking.setUser(user);
//            booking.setGymPackage(gymPackage);
//            booking.setGymClass(gymClass);
//            booking.setCoach(coach);
//            booking.setLocation(location);
//
//            booking.setBookingDate(LocalDate.parse(dto.getBookingDate()));
//            booking.setEndDate(LocalDate.parse(dto.getEndDate()));
//            booking.setNumberOfSessions(dto.getNumberOfSessions());
//            booking.setPackagePrice(dto.getPackagePrice());
//            booking.setCoachFee(dto.getCoachFee());
//            booking.setTotalPrice(dto.getTotalPrice());
//            booking.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
//
//            bookingRepository.save(booking);
//
//            // coach mark as booked
//            coach.setBooked("YES");
//            guideRepository.save(coach);
//
//            return true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ===== Get Total Bookings =====
//    @Override
//    public int getTotalBookings() {
//        return (int) bookingRepository.count();
//    }
//
//    // ===== Get All Bookings =====
//    @Override
//    public List<BookingDTO> getAllBookings() {
//        List<BookingEntity> bookings = bookingRepository.findAll();
//        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    // ===== Get Bookings by User Email =====
//    @Override
//    public List<BookingDTO> getBookingsByUserEmail(String email) {
//        List<BookingEntity> bookings = bookingRepository.findByUser_Email(email);
//        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    // ===== Dashboard Reports =====
//    @Override
//    public List<Map<String, Object>> getBookingsPerDay() {
//        return bookingRepository.findBookingsPerDay();
//    }
//
//    @Override
//    public List<Map<String, Object>> getTotalPricePerBooking() {
//        return bookingRepository.findTotalPricePerBooking();
//    }
//
//    // ===== Entity -> DTO Converter =====
//    private BookingDTO convertToDTO(BookingEntity booking) {
//        BookingDTO dto = new BookingDTO();
//        dto.setPackageName(booking.getGymPackage().getName());
//        dto.setClassName(booking.getGymClass().getName());
//        dto.setCoachEmail(booking.getCoach().getEmail());
//        dto.setLocationName(booking.getLocation().getName());
//        dto.setUserEmail(booking.getUser().getEmail());
//
//        dto.setBookingDate(booking.getBookingDate().toString());
//        dto.setEndDate(booking.getEndDate().toString());
//        dto.setNumberOfSessions(booking.getNumberOfSessions());
//        dto.setPackagePrice(booking.getPackagePrice());
//        dto.setCoachFee(booking.getCoachFee());
//        dto.setTotalPrice(booking.getTotalPrice());
//        dto.setStatus(booking.getStatus());
//
//        return dto;
//    }
//}


package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.dto.BookingDTO;
import org.example.aad_gymnest.entity.*;
import org.example.aad_gymnest.repo.*;
import org.example.aad_gymnest.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    @Override
    public boolean saveBooking(BookingDTO dto) {

        // --- Validate Package ---
        PackageEntity gymPackage = packageRepository.findByNameIgnoreCase(dto.getPackageName().trim())
                .orElseThrow(() -> new RuntimeException("Package '" + dto.getPackageName() + "' not found"));

        // --- Validate Class ---
        ClassEntity gymClass = classRepository.findByNameIgnoreCase(dto.getClassName().trim())
                .orElseThrow(() -> new RuntimeException("Class '" + dto.getClassName() + "' not found"));

        // --- Validate Coach ---
        GuideEntity coach = guideRepository.findByEmail(dto.getCoachEmail().trim())
                .orElseThrow(() -> new RuntimeException("Coach with email '" + dto.getCoachEmail() + "' not found"));

        // --- Validate Location ---
        LocationEntity location = locationRepository.findByNameIgnoreCase(dto.getLocationName().trim())
                .orElseThrow(() -> new RuntimeException("Location '" + dto.getLocationName() + "' not found"));

        // --- Validate User ---
        UserEntity user = userRepository.findByEmail(dto.getUserEmail().trim());
        if (user == null) {
            throw new RuntimeException("User with email '" + dto.getUserEmail() + "' not found");
        }

        // --- Create Booking ---
        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
        booking.setGymPackage(gymPackage);
        booking.setGymClass(gymClass);
        booking.setCoach(coach);
        booking.setLocation(location);
        booking.setBookingDate(LocalDate.parse(dto.getBookingDate()));
        booking.setEndDate(LocalDate.parse(dto.getEndDate()));
        booking.setNumberOfSessions(dto.getNumberOfSessions());
        booking.setPackagePrice(dto.getPackagePrice());
        booking.setCoachFee(dto.getCoachFee());
        booking.setTotalPrice(dto.getTotalPrice());
        booking.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");

        bookingRepository.save(booking);

        // --- Mark coach as booked ---
        coach.setBooked("YES");
        guideRepository.save(coach);

        return true;
    }

    @Override
    public int getTotalBookings() {
        return (int) bookingRepository.count();
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByUserEmail(String email) {
        return bookingRepository.findByUser_Email(email)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getBookingsPerDay() {
        return bookingRepository.findBookingsPerDay();
    }

    @Override
    public List<Map<String, Object>> getTotalPricePerBooking() {
        return bookingRepository.findTotalPricePerBooking();
    }

    private BookingDTO convertToDTO(BookingEntity booking) {
        BookingDTO dto = new BookingDTO();
        dto.setPackageName(booking.getGymPackage().getName());
        dto.setClassName(booking.getGymClass().getName());
        dto.setCoachEmail(booking.getCoach().getEmail());
        dto.setLocationName(booking.getLocation().getName());
        dto.setUserEmail(booking.getUser().getEmail());
        dto.setBookingDate(booking.getBookingDate().toString());
        dto.setEndDate(booking.getEndDate().toString());
        dto.setNumberOfSessions(booking.getNumberOfSessions());
        dto.setPackagePrice(booking.getPackagePrice());
        dto.setCoachFee(booking.getCoachFee());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());
        return dto;
    }
}
