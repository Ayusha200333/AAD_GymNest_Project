package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.AttendanceDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.AttendanceService;
import org.example.aad_gymnest.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342") // Frontend URL
@RestController
@RequestMapping("api/v1/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/mark")
    public ResponseEntity<ResponseDTO> markAttendance(@RequestBody AttendanceRequest request) {
        boolean success = attendanceService.markAttendance(
                request.getEmail(),
                request.getDate(),
                request.getType()
        );

        if (success) {
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Attendance marked", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Member not found", false));
        }
    }

    // ✅ Both ADMIN and USER can fetch attendance
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getAttendance(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String search) {

        List<AttendanceDTO> list = attendanceService.getAttendance(date, search);
        return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Attendance fetched", list));
    }

    // Request DTO
    public static class AttendanceRequest {
        private String email;
        private LocalDate date;
        private String type; // checkin / checkout

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}
