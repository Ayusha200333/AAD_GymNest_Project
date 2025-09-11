//package org.example.aad_gymnest.service;
//
//import org.example.aad_gymnest.dto.AttendanceDTO;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public interface AttendanceService {
//    boolean markAttendance(String email, LocalDate date, String type, boolean isAdmin);
//    List<AttendanceDTO> getAttendance(LocalDate date, String search);
//}


package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.AttendanceDTO;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    boolean markAttendance(String email, LocalDate date, String type);
    List<AttendanceDTO> getAttendance(LocalDate date, String search);
}
