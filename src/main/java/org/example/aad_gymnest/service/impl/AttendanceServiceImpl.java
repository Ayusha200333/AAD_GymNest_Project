//package org.example.aad_gymnest.service.impl;
//
//import org.example.aad_gymnest.entity.AttendanceEntity;
//import org.example.aad_gymnest.service.AttendanceService;
//import org.example.aad_gymnest.dto.AttendanceDTO;
//import org.example.aad_gymnest.entity.UserEntity;
//import org.example.aad_gymnest.repo.AttendanceRepository;
//import org.example.aad_gymnest.repo.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class AttendanceServiceImpl implements AttendanceService {
//
//    @Autowired
//    private AttendanceRepository attendanceRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public boolean markAttendance(String email, LocalDate date, String type, boolean isAdmin) {
//        // Security checks for non-admin users
//        if (!isAdmin && !date.equals(LocalDate.now())) {
//            throw new AccessDeniedException("You can only mark attendance for today");
//        }
//
//        UserEntity member = userRepository.findByEmail(email);
//        if (member == null) return false;
//
//        Optional<AttendanceEntity> optionalAttendance = attendanceRepository.findByMemberAndDate(member, date);
//        AttendanceEntity attendance = optionalAttendance.orElse(new AttendanceEntity());
//
//        // If creating new attendance record, set member and date
//        if (attendance.getMember() == null) {
//            attendance.setMember(member);
//            attendance.setDate(date);
//        }
//
//        if ("checkin".equalsIgnoreCase(type)) {
//            attendance.setCheckIn(LocalTime.now());
//        } else if ("checkout".equalsIgnoreCase(type)) {
//            // Can't check out without checking in first
//            if (attendance.getCheckIn() == null) {
//                throw new IllegalStateException("Cannot check out without checking in first");
//            }
//            attendance.setCheckOut(LocalTime.now());
//        }
//
//        // Determine status based on check-in time (for morning sessions)
//        if (attendance.getCheckIn() != null && attendance.getCheckIn().isAfter(LocalTime.of(9, 0))) {
//            attendance.setStatus("Late");
//        } else if (attendance.getCheckIn() != null) {
//            attendance.setStatus("Present");
//        }
//
//        attendanceRepository.save(attendance);
//        return true;
//    }
//
//    @Override
//    public List<AttendanceDTO> getAttendance(LocalDate date, String search) {
//        List<AttendanceEntity> attendances;
//
//        if (date != null) {
//            attendances = attendanceRepository.findByDate(date);
//        } else if (search != null && !search.isEmpty()) {
//            attendances = attendanceRepository.findByMember_NameContainingIgnoreCaseOrMember_EmailContainingIgnoreCase(search, search);
//        } else {
//            attendances = attendanceRepository.findAll();
//        }
//
//        return attendances.stream().map(a -> {
//            AttendanceDTO dto = new AttendanceDTO();
//            dto.setMemberName(a.getMember().getName());
//            dto.setEmail(a.getMember().getEmail());
//            dto.setDate(a.getDate());
//            dto.setCheckIn(a.getCheckIn());
//            dto.setCheckOut(a.getCheckOut());
//            dto.setStatus(a.getStatus());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//}



package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.dto.AttendanceDTO;
import org.example.aad_gymnest.entity.AttendanceEntity;
import org.example.aad_gymnest.entity.UserEntity;
import org.example.aad_gymnest.repo.AttendanceRepository;
import org.example.aad_gymnest.repo.UserRepository;
import org.example.aad_gymnest.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean markAttendance(String email, LocalDate date, String type) {
        Optional<UserEntity> userOpt = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOpt.isEmpty()) {
            return false; // user not found
        }
        UserEntity member = userOpt.get();

        // Fetch or create attendance record
        Optional<AttendanceEntity> existingOpt = attendanceRepository.findByMemberAndDate(member, date);
        AttendanceEntity attendance = existingOpt.orElseGet(() -> {
            AttendanceEntity a = new AttendanceEntity();
            a.setMember(member);
            a.setDate(date);
            return a;
        });

        // Handle checkin
        if ("checkin".equalsIgnoreCase(type)) {
            if (attendance.getCheckIn() == null) {
                attendance.setCheckIn(LocalTime.now());

                // Mark Late if check-in is after 9 AM
                if (attendance.getCheckIn().isAfter(LocalTime.of(9, 0))) {
                    attendance.setStatus("Late");
                } else {
                    attendance.setStatus("Present");
                }
            } else {
                // Already checked in
                throw new IllegalStateException("Already checked in for today.");
            }
        }
        // Handle checkout
        else if ("checkout".equalsIgnoreCase(type)) {
            if (attendance.getCheckIn() == null) {
                throw new IllegalStateException("Cannot check out without checking in first.");
            }
            if (attendance.getCheckOut() == null) {
                attendance.setCheckOut(LocalTime.now());
                attendance.setStatus("Completed");
            } else {
                throw new IllegalStateException("Already checked out for today.");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid attendance type. Use 'checkin' or 'checkout'.");
        }

        attendanceRepository.save(attendance);
        return true;
    }

    @Override
    public List<AttendanceDTO> getAttendance(LocalDate date, String search) {
        List<AttendanceEntity> records;

        if (date != null && search != null && !search.isEmpty()) {
            records = attendanceRepository
                    .findByDateAndMember_NameContainingIgnoreCaseOrDateAndMember_EmailContainingIgnoreCase(
                            date, search, date, search);
        } else if (date != null) {
            records = attendanceRepository.findByDate(date);
        } else if (search != null && !search.isEmpty()) {
            records = attendanceRepository
                    .findByMember_NameContainingIgnoreCaseOrMember_EmailContainingIgnoreCase(
                            search, search);
        } else {
            records = attendanceRepository.findAll();
        }

        return records.stream()
                .map(a -> {
                    AttendanceDTO dto = new AttendanceDTO();
                    dto.setId(a.getId());
                    dto.setDate(a.getDate());
                    dto.setMemberName(a.getMember().getName());
                    dto.setEmail(a.getMember().getEmail());
                    dto.setCheckIn(a.getCheckIn());
                    dto.setCheckOut(a.getCheckOut());
                    dto.setStatus(a.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
