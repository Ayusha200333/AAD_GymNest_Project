package org.example.aad_gymnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ClassDTO {
        private Long id;
        private String name;
        private String trainer;
        private String day;
        private String time;
        private int capacity;
        private int enrolled;
        private String status;
}


