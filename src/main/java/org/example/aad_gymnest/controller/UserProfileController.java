package org.example.aad_gymnest.controller;
//
//import jakarta.validation.Valid;
//import org.example.aad_gymnest.dto.AuthDTO;
//import org.example.aad_gymnest.dto.ResponseDTO;
//import org.example.aad_gymnest.dto.UserDTO;
//import org.example.aad_gymnest.dto.UserProfileDTO;
//import org.example.aad_gymnest.service.UserProfileService;
//import org.example.aad_gymnest.service.UserService;
//import org.example.aad_gymnest.util.JwtUtil;
//import org.example.aad_gymnest.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:63342")
//@RestController
//@RequestMapping("api/v1/user")
public class UserProfileController {
//
//    private final UserProfileService userProfileService;
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    public UserProfileController(UserProfileService userProfileService, JwtUtil jwtUtil) {
//        this.userProfileService = userProfileService;
//        this.jwtUtil = jwtUtil;
//    }
//
//    // ------------------ REGISTER ------------------
//    @PostMapping("/register")
//    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserProfileDTO userProfileDTO) {
//        try {
//            int res = userProfileService.saveUser(userProfileDTO);
//            if (res == VarList.Created) {
//                String token = jwtUtil.generateToken(userProfileDTO);
//                AuthDTO authDTO = new AuthDTO(userProfileDTO.getEmail(), token);
//                return ResponseEntity.status(HttpStatus.CREATED)
//                        .body(new ResponseDTO(VarList.Created, "User Registered Successfully", authDTO));
//            } else if (res == VarList.Not_Acceptable) {
//                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                        .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
//        }
//    }
//
//    // ------------------ GET USER BY EMAIL ------------------
//    @GetMapping("/profile")
//    public ResponseEntity<ResponseDTO> getUserByEmail(@RequestParam String email) {
//        UserProfileDTO user = userProfileService.getUserByEmail(email);
//        if (user != null) {
//            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Profile Retrieved Successfully", user));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
//    }
//
//    // ------------------ UPDATE PROFILE ------------------
//    @PutMapping("/update")
//    public ResponseEntity<ResponseDTO> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
//        boolean updated = userProfileService.updateUser(userProfileDTO);
//        if (updated) {
//            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Profile Updated Successfully", userProfileDTO));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
//    }
//
//    // ------------------ GET ALL USERS ------------------
//    @GetMapping("/getAll")
//    public ResponseEntity<ResponseDTO> getAllUsers() {
//        List<UserProfileDTO> users = userProfileService.getAllUsers();
//        if (users.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseDTO(VarList.Not_Found, "No Users Found", null));
//        }
//        return ResponseEntity.ok(new ResponseDTO(VarList.Created, "All Users Retrieved", users));
//    }
//
//    // ------------------ DELETE USER ------------------
//    @DeleteMapping("/delete/{email}")
//    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String email) {
//        boolean deleted = userProfileService.deleteUserByEmail(email);
//        if (deleted) {
//            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "User Deleted Successfully", null));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
//    }
}
