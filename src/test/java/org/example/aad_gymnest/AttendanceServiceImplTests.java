package org.example.aad_gymnest;

import org.example.aad_gymnest.dto.AttendanceDTO;
import org.example.aad_gymnest.entity.AttendanceEntity;
import org.example.aad_gymnest.entity.UserEntity;
import org.example.aad_gymnest.repo.AttendanceRepository;
import org.example.aad_gymnest.repo.UserRepository;
import org.example.aad_gymnest.service.impl.AttendanceServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTests {

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private UserRepository userRepository;

    private UserEntity user;
    private AttendanceEntity attendance;


    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUid(UUID.randomUUID()); // <-- id → uid
        user.setName("Test User");
        user.setEmail("test@example.com");

        attendance = new AttendanceEntity();
        attendance.setId(UUID.randomUUID());
        attendance.setMember(user);
        attendance.setDate(LocalDate.now());
    }

    @Test
    void shouldMarkCheckInSuccessfully() {
        // arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(attendanceRepository.findByMemberAndDate(user, LocalDate.now())).thenReturn(Optional.empty());
        when(attendanceRepository.save(any(AttendanceEntity.class))).thenReturn(attendance);

        // act
        boolean result = attendanceService.markAttendance("test@example.com", LocalDate.now(), "checkin");

        // assert
        assertTrue(result);
        verify(attendanceRepository, times(1)).save(any(AttendanceEntity.class));
    }

    @Test
    void shouldThrowWhenAlreadyCheckedIn() {
        // arrange
        attendance.setCheckIn(LocalTime.now());
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(attendanceRepository.findByMemberAndDate(user, LocalDate.now())).thenReturn(Optional.of(attendance));

        // act & assert
        assertThrows(IllegalStateException.class, () ->
                attendanceService.markAttendance("test@example.com", LocalDate.now(), "checkin"));
    }

    @Test
    void shouldMarkCheckOutSuccessfully() {
        // arrange
        attendance.setCheckIn(LocalTime.of(8, 30));
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(attendanceRepository.findByMemberAndDate(user, LocalDate.now())).thenReturn(Optional.of(attendance));
        when(attendanceRepository.save(any(AttendanceEntity.class))).thenReturn(attendance);

        // act
        boolean result = attendanceService.markAttendance("test@example.com", LocalDate.now(), "checkout");

        // assert
        assertTrue(result);
        assertNotNull(attendance.getCheckOut());
        assertEquals("Completed", attendance.getStatus());
        verify(attendanceRepository, times(1)).save(attendance);
    }

    @Test
    void shouldThrowWhenCheckOutWithoutCheckIn() {
        // arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(attendanceRepository.findByMemberAndDate(user, LocalDate.now())).thenReturn(Optional.of(attendance));

        // act & assert
        assertThrows(IllegalStateException.class, () ->
                attendanceService.markAttendance("test@example.com", LocalDate.now(), "checkout"));
    }

    @Test
    void shouldReturnAttendanceList() {
        // arrange
        attendance.setCheckIn(LocalTime.of(8, 0));
        attendance.setCheckOut(LocalTime.of(16, 0));
        attendance.setStatus("Present");

        List<AttendanceEntity> records = List.of(attendance);
        when(attendanceRepository.findAll()).thenReturn(records);

        // act
        List<AttendanceDTO> result = attendanceService.getAttendance(null, null);

        // assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getMemberName());
        assertEquals("test@example.com", result.get(0).getEmail());
    }
}
