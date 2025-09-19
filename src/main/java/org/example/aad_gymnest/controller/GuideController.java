package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.GuideDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.EmailService;
import org.example.aad_gymnest.service.GuideService;
import org.example.aad_gymnest.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/guide")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @Autowired
    private EmailService emailService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveGuide(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam(value = "imageUrl", required = false) MultipartFile image,
            @RequestParam("description") String description,
            @RequestParam("paymentPerHour") String paymentPerHour,
            @RequestParam("phone") String phone) {

        try {
            GuideDTO guideDTO = new GuideDTO();
            guideDTO.setFullName(fullName);
            guideDTO.setEmail(email);
            guideDTO.setDescription(description);
            guideDTO.setPaymentPerHour(paymentPerHour);
            guideDTO.setPhone(phone);
            guideDTO.setStatus("ACTIVE");
            guideDTO.setBooked("NO");

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                guideDTO.setImageUrl(imagePath);
            }

            int res = guideService.saveGuide(guideDTO);

            if (res == VarList.Created) {
                try {
                    emailService.sendGuideRegistrationEmail(email, fullName);
                } catch (Exception e) {
                    System.err.println("Email sending failed but guide saved: " + e.getMessage());
                }
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Guide Saved Successfully", guideDTO));
            } else if (res == VarList.Not_Acceptable) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Guide Already Exists", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }

    }


    @PostMapping("/update/{email}")
    public ResponseEntity<ResponseDTO> updateGuide(
            @PathVariable String email,
            @RequestParam("editGuideName") String fullName,
            @RequestParam("editGuideDescription") String description,
            @RequestParam(value = "editGuideImage", required = false) MultipartFile image,
            @RequestParam("editGuidePayment") String paymentPerHour,
            @RequestParam("editGuidePhone") String phone) {

        try {
            GuideDTO guideDTO = new GuideDTO();
            guideDTO.setFullName(fullName);
            guideDTO.setDescription(description);
            guideDTO.setPaymentPerHour(paymentPerHour);
            guideDTO.setPhone(phone);

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                guideDTO.setImageUrl(imagePath);
            }

            int res = guideService.updateGuide(email, guideDTO);

            if (res == VarList.Created) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.Created, "Guide Updated Successfully", guideDTO));
            } else if (res == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Guide Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping("/deactivate/{email}")
    public ResponseEntity<ResponseDTO> deactivateGuide(@PathVariable String email) {
        try {
            int res = guideService.deactivateGuide(email);
            if (res == VarList.Created) {
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Guide Deactivated Successfully", null));
            } else if (res == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Guide Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping("/activate/{email}")
    public ResponseEntity<ResponseDTO> activateGuide(@PathVariable String email) {
        try {
            int res = guideService.activateGuide(email);
            if (res == VarList.Created) {
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Guide Activated Successfully", null));
            } else if (res == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Guide Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllGuides() {
        try {
            List<GuideDTO> allGuides = guideService.getAllGuides();
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "All Guides Retrieved Successfully", allGuides));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<ResponseDTO> getAvailableGuides() {
        try {
            List<GuideDTO> availableGuides = guideService.getAvailableGuides();
            if (availableGuides.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO(VarList.Not_Found, "No available guides at the moment", null));
            } else {
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Available Guides Retrieved Successfully", availableGuides));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ResponseDTO> deleteGuide(@PathVariable String email) {
        try {
            int res = guideService.deleteGuide(email); // Ensure this method exists in GuideService
            if (res == VarList.Created) {
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Guide Deleted Successfully", null));
            } else if (res == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Guide Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

}
