package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.dto.PaymentDTO;
import org.example.aad_gymnest.entity.PaymentEntity;
import org.example.aad_gymnest.repo.PaymentRepository;
import org.example.aad_gymnest.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public boolean savePayment(PaymentDTO dto){
        try{
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setUserEmail(dto.getUserEmail());
            paymentEntity.setCardHolderName(dto.getCardHolderName());
            paymentEntity.setCardNumber(dto.getCardNumber());
            paymentEntity.setExpiryDate(dto.getExpiryDate());
            paymentEntity.setCvv(dto.getCvv());
            paymentEntity.setAmount(dto.getAmount());
            paymentEntity.setPaymentDate(LocalDateTime.now());

            paymentRepository.save(paymentEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Map<String,Object>> getAllPayments(){
        List<PaymentEntity> payments = paymentRepository.findAll();
        System.out.println(payments);
        return payments.stream().map(paymentEntity -> {
            Map<String,Object> paymentDetails = new HashMap<>();
            paymentDetails.put("id",paymentEntity.getId());
            paymentDetails.put("userEmail",paymentEntity.getUserEmail());
            paymentDetails.put("amount",paymentEntity.getAmount());
            paymentDetails.put("paymentDate",paymentEntity.getPaymentDate());
            return paymentDetails;
        }).collect(Collectors.toList());
    }
}
