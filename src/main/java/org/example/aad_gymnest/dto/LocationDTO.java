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
        private String name;
        private String description;
        private String imageUrl;
        private String address;
        private String openHours;
}
