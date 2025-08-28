package org.example.aad_gymnest.controller;

import lombok.RequiredArgsConstructor;
import org.example.aad_gymnest.dto.APIResponse;
import org.example.aad_gymnest.dto.AuthDTO;
import org.example.aad_gymnest.dto.RegisterDTO;
import org.example.aad_gymnest.service.impl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(
            @RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "User registered successfully",
                        authService.register(registerDTO)
                )
        );
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(new APIResponse(200,
                "OK",authService.authenticate(authDTO)));
    }
}
