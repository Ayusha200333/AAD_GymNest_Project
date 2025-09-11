package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.PaymentDTO;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    boolean savePayment(PaymentDTO paymentDTO);
    List<Map<String,Object>> getAllPayments();
}
