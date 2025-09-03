package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PackageDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<String> address;
    private String openHours;
    private String imageUrl;
}
