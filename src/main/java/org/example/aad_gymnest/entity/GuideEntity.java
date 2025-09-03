package org.example.aad_gymnest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guides")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    private String imageUrl;
    private String description;
    private String paymentPerHour;
    private String phone;
    private String status;
    private String booked;
}
