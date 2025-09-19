//package org.example.aad_gymnest.controller;
//
//import org.example.aad_gymnest.dto.DashboardDTO;
//import org.example.aad_gymnest.dto.ResponseDTO;
//import org.example.aad_gymnest.service.*;
//import org.example.aad_gymnest.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/v1/dashboard")
//@CrossOrigin
//public class DashboardController {
//    @Autowired
//    private GuideService guideService;
//    @Autowired
//    private UserService userServiceService;
//    @Autowired
//    private BookingService bookingService;
//    @Autowired
//    private ClassService classService;
//    @Autowired
//    private LocationService locationService;
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/counts")
//    public ResponseEntity<ResponseDTO> getCounts() {
//        try {
//            DashboardDTO dashboardDTO = new DashboardDTO();
//            dashboardDTO.setTotalMembers(userService.getTotalMemberCount());
//            dashboardDTO.setActiveMemberships(userService.getActiveMembershipCount());
//            dashboardDTO.setTotalGuides(guideService.getTotalGuideCount());
//            dashboardDTO.setPerDayBookings(bookingService.getTodayBookingCount());
//            dashboardDTO.setTotalClasses(classService.getTotalClassCount());
//            dashboardDTO.setTotalLocations(locationService.getTotalLocationCount());
//
//            return ResponseEntity.ok(
//                    new ResponseDTO(VarList.Created, "Dashboard counts loaded successfully", dashboardDTO)
//            );
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
//        }
//    }
//    }




package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.DashboardDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.*;
import org.example.aad_gymnest.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private GuideService guideService;

    @Autowired
    private UserService userService; // Fixed duplicate name

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClassService classService;

    @Autowired
    private LocationService locationService;

    // ===== COUNTS =====
    @GetMapping("/counts")
    public ResponseEntity<ResponseDTO> getCounts() {
        try {
            DashboardDTO dashboardDTO = new DashboardDTO();
            dashboardDTO.setTotalMembers(userService.getTotalMemberCount());
            dashboardDTO.setActiveMemberships(userService.getActiveMembershipCount());
            dashboardDTO.setTotalGuides(guideService.getTotalGuideCount());
            dashboardDTO.setPerDayBookings(bookingService.getTodayBookingCount());
            dashboardDTO.setTotalClasses(classService.getTotalClassCount());
            dashboardDTO.setTotalLocations(locationService.getTotalLocationCount());

            return ResponseEntity.ok(
                    new ResponseDTO(VarList.Created, "Dashboard counts loaded successfully", dashboardDTO)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    // ===== MEMBERSHIP GROWTH =====
    @GetMapping("/membership-growth")
    public ResponseEntity<ResponseDTO> getMembershipGrowth() {
        try {
            // ⚠️ REPLACE WITH ACTUAL SERVICE LOGIC LATER
            String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
            int[] values = {30, 45, 60, 80, 95, 120};

            Map<String, Object> data = new HashMap<>();
            data.put("labels", labels);
            data.put("values", values);

            return ResponseEntity.ok(
                    new ResponseDTO(VarList.Created, "Membership growth data loaded", data)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    // ===== REVENUE BREAKDOWN =====
    @GetMapping("/revenue-breakdown")
    public ResponseEntity<ResponseDTO> getRevenueBreakdown() {
        try {
            // ⚠️ REPLACE WITH ACTUAL SERVICE LOGIC LATER
            String[] labels = {"Memberships", "Classes", "Personal Training"};
            double[] values = {65.0, 25.0, 10.0};

            Map<String, Object> data = new HashMap<>();
            data.put("labels", labels);
            data.put("values", values);

            return ResponseEntity.ok(
                    new ResponseDTO(VarList.Created, "Revenue breakdown loaded", data)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}