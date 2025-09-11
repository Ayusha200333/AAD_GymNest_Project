package org.example.aad_gymnest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private double amount;

    private LocalDateTime paymentDate;
}
