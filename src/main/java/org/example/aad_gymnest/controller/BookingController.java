package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.BookingDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            bookingService.saveBooking(bookingDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(201, "Booking Saved Successfully", bookingDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookings() {
        try {
            List<BookingDTO> allBookings = bookingService.getAllBookings();
            return ResponseEntity.ok(new ResponseDTO(200, "Bookings Retrieved Successfully", allBookings));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, e.getMessage(), null));
        }
    }

    // ===== Get Bookings by User Email =====
    @GetMapping("/getBookings/{email}")
    public ResponseEntity<ResponseDTO> getBookingsByUserEmail(@PathVariable String email) {
        try {
            List<BookingDTO> bookings = bookingService.getBookingsByUserEmail(email);
            return ResponseEntity.ok(new ResponseDTO(200, "Bookings Retrieved Successfully", bookings));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, e.getMessage(), null));
        }
    }

}

