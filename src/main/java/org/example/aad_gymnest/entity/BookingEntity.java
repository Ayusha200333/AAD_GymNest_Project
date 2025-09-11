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

    // === Relationships ===
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;   // Member who books

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private PackageEntity gymPackage;   // Membership Package

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity gymClass;   // Gym Class (Yoga, Cardio etc.)

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private GuideEntity coach;   // Assigned Coach (GuideEntity acts as Coach)

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;   // Gym branch / Location

    // === Booking Details ===
    private LocalDate bookingDate;   // Start date
    private LocalDate endDate;       // End date

    private int numberOfSessions;    // Number of sessions booked

    private double packagePrice;     // Package price
    private double coachFee;         // Extra fee for coach
    private double totalPrice;       // packagePrice + coachFee

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE / COMPLETED / CANCELLED
}
