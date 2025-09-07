package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceDTO {
        private String memberName;
        private String email;
        private LocalDate date;
        private LocalTime checkIn;
        private LocalTime checkOut;
        private String status;     // Present, Absent, Late
}

