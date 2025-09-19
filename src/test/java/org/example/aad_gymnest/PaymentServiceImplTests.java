package org.example.aad_gymnest;

import org.example.aad_gymnest.dto.PaymentDTO;
import org.example.aad_gymnest.entity.PaymentEntity;
import org.example.aad_gymnest.repo.PaymentRepository;
import org.example.aad_gymnest.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTests {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private PaymentDTO paymentDTO;
    private PaymentEntity paymentEntity;

    @BeforeEach
    void setUp() {
        paymentDTO = new PaymentDTO();
        paymentDTO.setUserEmail("test@example.com");
        paymentDTO.setCardHolderName("John Doe");
        paymentDTO.setCardNumber("1234567890123456");
        paymentDTO.setExpiryDate("12/26");
        paymentDTO.setCvv("123");
        paymentDTO.setAmount(1000.0);

        paymentEntity = new PaymentEntity();
        paymentEntity.setId(1L);
        paymentEntity.setUserEmail("test@example.com");
        paymentEntity.setCardHolderName("John Doe");
        paymentEntity.setCardNumber("1234567890123456");
        paymentEntity.setExpiryDate("12/26");
        paymentEntity.setCvv("123");
        paymentEntity.setAmount(1000.0);
        paymentEntity.setPaymentDate(LocalDateTime.now());
    }

    @Test
    void shouldSavePaymentSuccessfully() {
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

        boolean result = paymentService.savePayment(paymentDTO);

        assertTrue(result);
        verify(paymentRepository, times(1)).save(any(PaymentEntity.class));
    }

    @Test
    void shouldReturnFalseWhenSaveFails() {
        when(paymentRepository.save(any(PaymentEntity.class))).thenThrow(new RuntimeException("DB error"));

        boolean result = paymentService.savePayment(paymentDTO);

        assertFalse(result);
        verify(paymentRepository, times(1)).save(any(PaymentEntity.class));
    }

    @Test
    void shouldReturnAllPayments() {
        when(paymentRepository.findAll()).thenReturn(List.of(paymentEntity));

        List<Map<String, Object>> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).get("userEmail"));
        assertEquals(1000.0, result.get(0).get("amount"));
    }

    @Test
    void shouldReturnEmptyListWhenNoPayments() {
        when(paymentRepository.findAll()).thenReturn(List.of());

        List<Map<String, Object>> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
