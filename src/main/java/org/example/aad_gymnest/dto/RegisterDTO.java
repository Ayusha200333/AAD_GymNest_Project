package org.example.aad_gymnest.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
}
