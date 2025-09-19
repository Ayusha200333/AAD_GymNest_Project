package org.example.aad_gymnest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private PackageEntity gymPackage;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity gymClass;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private GuideEntity coach;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;

    private LocalDate bookingDate;
    private LocalDate endDate;

    private int numberOfSessions;

    private double packagePrice;
    private double coachFee;
    private double totalPrice;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE / COMPLETED / CANCELLED
}
