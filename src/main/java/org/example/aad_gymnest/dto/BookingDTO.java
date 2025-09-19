package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private String packageName;     // Monthly/Yearly plan
    private String className;
    private String coachEmail;
    private String locationName;
    private String userEmail;

    private String bookingDate;
    private String endDate;

    private int numberOfSessions;
    private double packagePrice;
    private double coachFee;
    private double totalPrice;

    private String status;          // ACTIVE / COMPLETED / CANCELLED
}
