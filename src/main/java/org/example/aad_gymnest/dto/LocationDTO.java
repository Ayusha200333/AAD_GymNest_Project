package org.example.aad_gymnest.dto;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class LocationDTO {
        private Long id;
        private String name;       // Branch Name (Ex: Colombo 03)
        private String description; // Description / Opening hours
        private String imageUrl;    // Location image
        private String address;     // Address or location link
        private String openHours;   // "Open 6am - 10pm"
}
