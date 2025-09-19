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

        Optional<AttendanceEntity> existingOpt = attendanceRepository.findByMemberAndDate(member, date);
        AttendanceEntity attendance = existingOpt.orElseGet(() -> {
            AttendanceEntity a = new AttendanceEntity();
            a.setMember(member);
            a.setDate(date);
            return a;
        });

        if ("checkin".equalsIgnoreCase(type)) {
            if (attendance.getCheckIn() == null) {
                attendance.setCheckIn(LocalTime.now());

                if (attendance.getCheckIn().isAfter(LocalTime.of(9, 0))) {
                    attendance.setStatus("Late");
                } else {
                    attendance.setStatus("Present");
                }
            } else {
                throw new IllegalStateException("Already checked in for today.");
            }
        }
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
