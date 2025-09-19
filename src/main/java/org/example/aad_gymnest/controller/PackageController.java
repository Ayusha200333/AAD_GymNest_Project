package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.PackageDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.PackageService;
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
@RequestMapping("api/v1/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> savePackage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("openHours") String openHours,
            @RequestParam(value = "classIds", required = false) List<String> classIds,
            @RequestParam(value = "imageUrl", required = false) MultipartFile image
    ) {
        try {
            PackageDTO packageDTO = new PackageDTO();
            packageDTO.setName(name);
            packageDTO.setDescription(description);
            packageDTO.setPrice(price);
            packageDTO.setOpenHours(openHours);
            packageDTO.setClasses(classIds);

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                packageDTO.setImageUrl(imagePath);
            }

            int res = packageService.savePackage(packageDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Package Saved Successfully", packageDTO));
                case VarList.Not_Acceptable:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Package Already Exists", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
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

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updatePackage(
            @PathVariable Long id,
            @RequestParam("editMembershipName") String name,
            @RequestParam("editMembershipDescription") String description,
            @RequestParam("editMembershipPrice") Double price,
            @RequestParam("editMembershipOpenHours") String openHours,
            @RequestParam(value = "editMembershipClassIds", required = false) List<String> classIds,
            @RequestParam(value = "editMembershipImage", required = false) MultipartFile image
    ) {
        try {
            PackageDTO packageDTO = new PackageDTO();
            packageDTO.setName(name);
            packageDTO.setDescription(description);
            packageDTO.setPrice(price);
            packageDTO.setOpenHours(openHours);
            packageDTO.setClasses(classIds);

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                packageDTO.setImageUrl(imagePath);
            }

            int res = packageService.updatePackage(id, packageDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.Created, "Package Updated Successfully", packageDTO));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Package Not Found", null));
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
    public ResponseEntity<ResponseDTO> deletePackage(@PathVariable Long id) {
        try {
            int res = packageService.deletePackage(id);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.ok(
                            new ResponseDTO(VarList.Created, "Package Deleted Successfully", null));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Package Not Found", null));
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
    public ResponseEntity<ResponseDTO> getAllPackages() {
        try {
            List<PackageDTO> allPackages = packageService.getAllPackages();
            return ResponseEntity.ok(
                    new ResponseDTO(VarList.Created, "Memberships Retrieved Successfully", allPackages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
