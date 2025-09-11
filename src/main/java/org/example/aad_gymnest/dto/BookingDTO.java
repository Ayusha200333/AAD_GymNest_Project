package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private String packageName;     // From Package.name (e.g., Monthly/Yearly plan)
    private String className;       // From Class.name (e.g., Yoga, Cardio)
    private String coachEmail;      // From Coach.email
    private String locationName;    // From Location.name (branch/center)
    private String userEmail;       // From User.email (member)

    private String bookingDate;     // yyyy-MM-dd (convert to LocalDate in backend)
    private String endDate;         // membership/class end date

    private int numberOfSessions;   // How many sessions booked
    private double packagePrice;    // Base package price
    private double coachFee;        // Extra coach fee if any
    private double totalPrice;

    private String status;          // ACTIVE / COMPLETED / CANCELLED// packagePrice + coachFee
}
