package org.example.aad_gymnest.config;

import org.example.aad_gymnest.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class WebAppConfig {

    private final UserRepository userRepository;

    public WebAppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UserDetailsService Bean for Spring Security
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ModelMapper Bean for DTO mapping
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // PasswordEncoder Bean for Spring Security
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
