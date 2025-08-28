package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.LocationDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.LocationService;
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
@RequestMapping("api/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    private static final String UPLOAD_DIR = "src/main/resources/templates/uploads/";

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveLocation(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("address") String address,
            @RequestParam("openHours") String openHours,
            @RequestParam(value = "imageUrl", required = false) MultipartFile image) {
        try {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setName(name);
            locationDTO.setDescription(description);
            locationDTO.setAddress(address);
            locationDTO.setOpenHours(openHours);

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                locationDTO.setImageUrl(imagePath);
            }

            int res = locationService.saveLocation(locationDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Location Saved Successfully", locationDTO));
                case VarList.Not_Acceptable:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Location Already Exists", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateLocation(
            @PathVariable Long id,
            @RequestParam("editLocationName") String name,
            @RequestParam("editLocationDescription") String description,
            @RequestParam("editLocationAddress") String address,
            @RequestParam("editLocationOpenHours") String openHours,
            @RequestParam(value = "editLocationImage", required = false) MultipartFile image) {
        try {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setName(name);
            locationDTO.setDescription(description);
            locationDTO.setAddress(address);
            locationDTO.setOpenHours(openHours);

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                locationDTO.setImageUrl(imagePath);
            }

            int res = locationService.updateLocation(id, locationDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.Created, "Location Updated Successfully", locationDTO));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Location Not Found", null));
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
    public ResponseEntity<ResponseDTO> deleteLocation(@PathVariable Long id) {
        try {
            int res = locationService.deleteLocation(id);
            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.Created, "Location Deleted Successfully", null));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Location Not Found", null));
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
    public ResponseEntity<ResponseDTO> getAllLocations() {
        try {
            List<LocationDTO> allLocations = locationService.getAllLocations();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Created, "All Locations Retrieved Successfully", allLocations));
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
}
