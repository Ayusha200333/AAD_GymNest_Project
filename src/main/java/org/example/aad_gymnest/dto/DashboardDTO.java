package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//public class DashboardDTO {
//    private int totalGuides;
//}

public class DashboardDTO {
    private int totalMembers;
    private int activeMemberships;
    private int totalGuides;
    private int perDayBookings;
    private int totalClasses;
    private int totalLocations;
}
