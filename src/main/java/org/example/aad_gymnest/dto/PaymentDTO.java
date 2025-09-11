package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String userEmail;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv; //Card Verification Value
    private double amount;
}
