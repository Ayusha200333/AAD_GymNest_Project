package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.MembershipDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.MembershipService;
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
@RequestMapping("api/v1/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    private static final String UPLOAD_DIR = "src/main/resources/templates/uploads/";

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveMembership(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("address") List<String> address,
            @RequestParam("openHours") String openHours,
            @RequestParam(value = "imageUrl", required = false) MultipartFile image
    ) {
        try {
            // --- Prepare DTO ---
            MembershipDTO membershipDTO = new MembershipDTO();
            membershipDTO.setName(name);
            membershipDTO.setDescription(description);
            membershipDTO.setPrice(price);
            membershipDTO.setAddress(address);
            membershipDTO.setOpenHours(openHours);

            // --- Handle image upload ---
            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                membershipDTO.setImageUrl(imagePath);
            }

            // --- Save using service ---
            int res = membershipService.saveMembership(membershipDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Membership Saved Successfully", membershipDTO));
                case VarList.Not_Acceptable:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Membership Already Exists", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    // ---- Helper method for saving uploaded file ----
    private String saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName; // DB/store එකේ save වෙන්නේ මේ name එක
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateMembership(
            @PathVariable Long id,
            @RequestParam("editMembershipName") String name,
            @RequestParam("editMembershipDescription") String description,
            @RequestParam("editMembershipPrice") Double price,
            @RequestParam("editMembershipAddress") List<String> address,
            @RequestParam("editMembershipOpenHours") String openHours,
            @RequestParam(value = "editMembershipImage", required = false) MultipartFile image) {
        try {
            // --- Build DTO ---
            MembershipDTO membershipDTO = new MembershipDTO();
            membershipDTO.setName(name);
            membershipDTO.setDescription(description);
            membershipDTO.setPrice(price);
            membershipDTO.setAddress(address);
            membershipDTO.setOpenHours(openHours);

            // --- Handle image upload ---
            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                membershipDTO.setImageUrl(imagePath);
            }

            // --- Call Service ---
            int res = membershipService.updateMembership(id, membershipDTO);

            switch (res) {
                case VarList.Success:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.Success, "Membership Updated Successfully", membershipDTO));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Membership Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteMembership(@PathVariable Long id) {
        try {
            int res = membershipService.deleteMembership(id);

            switch (res) {
                case VarList.Success:
                    return ResponseEntity.ok(
                            new ResponseDTO(VarList.Success, "Membership Deleted Successfully", null));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Membership Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllMemberships() {
        try {
            List<MembershipDTO> allMemberships = membershipService.getAllMemberships();
            return ResponseEntity.ok(
                    new ResponseDTO(VarList.Success, "Memberships Retrieved Successfully", allMemberships));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
