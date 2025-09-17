package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.ClassDTO;
import java.util.List;
public interface ClassService {
        int saveClass(ClassDTO classDTO);
        int updateClass(Long id, ClassDTO classDTO);
        int deleteClass(Long id);
        List<ClassDTO> getAllClasses();
        List<ClassDTO> getAvailableClasses();



    int getTotalClassCount();
}

