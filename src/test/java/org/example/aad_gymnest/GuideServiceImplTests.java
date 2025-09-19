package org.example.aad_gymnest;

import org.example.aad_gymnest.dto.GuideDTO;
import org.example.aad_gymnest.entity.GuideEntity;
import org.example.aad_gymnest.repo.GuideRepository;
import org.example.aad_gymnest.service.impl.GuideServiceImpl;
import org.example.aad_gymnest.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuideServiceImplTests {

    @InjectMocks
    private GuideServiceImpl guideService;

    @Mock
    private GuideRepository guideRepository;

    @Mock
    private ModelMapper modelMapper;

    private GuideEntity guideEntity;
    private GuideDTO guideDTO;

    @BeforeEach
    void setUp() {
        guideEntity = new GuideEntity(
                1L,                       // id
                "John Doe",               // fullName
                "john@example.com",       // email
                null,                     // imageUrl
                "Tour guide for Colombo", // description
                "50",                     // paymentPerHour
                "123456789",              // phone
                "ACTIVE",                 // status
                "NO"                      // booked
        );

        guideDTO = new GuideDTO();
        guideDTO.setFullName("John Doe");
        guideDTO.setEmail("john@example.com");
        guideDTO.setDescription("Tour guide for Colombo");
        guideDTO.setPaymentPerHour("50");
        guideDTO.setPhone("123456789");
        guideDTO.setStatus("ACTIVE");
        guideDTO.setBooked("NO");
    }

    @Test
    void shouldSaveGuideSuccessfully() {
        when(guideRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(modelMapper.map(guideDTO, GuideEntity.class)).thenReturn(guideEntity);
        when(guideRepository.save(any(GuideEntity.class))).thenReturn(guideEntity);

        int result = guideService.saveGuide(guideDTO);

        assertEquals(VarList.Created, result);
        verify(guideRepository, times(1)).save(any(GuideEntity.class));
    }

    @Test
    void shouldNotSaveWhenGuideAlreadyExists() {
        when(guideRepository.existsByEmail("john@example.com")).thenReturn(true);

        int result = guideService.saveGuide(guideDTO);

        assertEquals(VarList.Not_Acceptable, result);
        verify(guideRepository, never()).save(any(GuideEntity.class));
    }

    @Test
    void shouldUpdateGuideSuccessfully() {
        when(guideRepository.findByEmail("john@example.com")).thenReturn(Optional.of(guideEntity));
        when(guideRepository.save(any(GuideEntity.class))).thenReturn(guideEntity);

        int result = guideService.updateGuide("john@example.com", guideDTO);

        assertEquals(VarList.Created, result);
        verify(guideRepository, times(1)).save(guideEntity);
    }

    @Test
    void shouldNotUpdateWhenGuideNotFound() {
        when(guideRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        int result = guideService.updateGuide("notfound@example.com", guideDTO);

        assertEquals(VarList.Not_Found, result);
        verify(guideRepository, never()).save(any(GuideEntity.class));
    }

    @Test
    void shouldDeactivateGuideSuccessfully() {
        when(guideRepository.findByEmail("john@example.com")).thenReturn(Optional.of(guideEntity));

        int result = guideService.deactivateGuide("john@example.com");

        assertEquals(VarList.Created, result);
        verify(guideRepository, times(1)).save(guideEntity);
    }

    @Test
    void shouldActivateGuideSuccessfully() {
        guideEntity.setStatus("INACTIVE");
        when(guideRepository.findByEmail("john@example.com")).thenReturn(Optional.of(guideEntity));

        int result = guideService.activateGuide("john@example.com");

        assertEquals(VarList.Created, result);
        verify(guideRepository, times(1)).save(guideEntity);
    }

    @Test
    void shouldReturnAllGuides() {
        when(guideRepository.findAll()).thenReturn(List.of(guideEntity));
        when(modelMapper.map(any(GuideEntity.class), eq(GuideDTO.class))).thenReturn(guideDTO);

        var result = guideService.getAllGuides();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
    }

    @Test
    void shouldReturnAvailableGuidesOnly() {
        GuideEntity bookedGuide = new GuideEntity(
                2L, "", "jane@example.com", null, "", "40", "987654321", "ACTIVE", "YES"
        );

        when(guideRepository.findAllByBookedAndStatus("NO", "ACTIVE")).thenReturn(List.of(guideEntity));
        when(modelMapper.map(any(GuideEntity.class), eq(GuideDTO.class))).thenReturn(guideDTO);

        var result = guideService.getAvailableGuides();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
    }

    @Test
    void shouldDeleteGuideSuccessfully() {
        when(guideRepository.findByEmail("john@example.com")).thenReturn(Optional.of(guideEntity));

        int result = guideService.deleteGuide("john@example.com");

        assertEquals(VarList.Created, result);
        verify(guideRepository, times(1)).delete(guideEntity);
    }

    @Test
    void shouldNotDeleteWhenGuideNotFound() {
        when(guideRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        int result = guideService.deleteGuide("notfound@example.com");

        assertEquals(VarList.Not_Found, result);
        verify(guideRepository, never()).delete(any(GuideEntity.class));
    }
}
