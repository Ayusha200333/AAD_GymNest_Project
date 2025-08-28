package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class GuideDTO {
    private String fullName;
    private String email;
    private String imageUrl;
    private String description;
    private String paymentPerHour;
    private String phone;
    private String status;
    private String booked;
}
