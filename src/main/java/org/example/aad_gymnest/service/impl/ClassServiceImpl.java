package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.dto.ClassDTO;
import org.example.aad_gymnest.entity.ClassEntity;
import org.example.aad_gymnest.repo.ClassRepository;
import org.example.aad_gymnest.service.ClassService;
import org.example.aad_gymnest.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

        @Autowired
        private ClassRepository classRepository;

        @Override
        public int saveClass(ClassDTO classDTO) {
            if(classRepository.findByName(classDTO.getName()).isPresent()) {
                return VarList.Not_Acceptable; // Class already exists
            }

            ClassEntity classEntity = new ClassEntity();
            classEntity.setName(classDTO.getName());
            classEntity.setTrainer(classDTO.getTrainer());
            classEntity.setDay(classDTO.getDay());
            classEntity.setTime(classDTO.getTime());
            classEntity.setCapacity(classDTO.getCapacity());
            classEntity.setEnrolled(0);
            classEntity.setStatus("AVAILABLE");

            classRepository.save(classEntity);
            return VarList.Created;
        }

        @Override
        public int updateClass(Long id, ClassDTO classDTO) {
            return classRepository.findById(id).map(c -> {
                c.setName(classDTO.getName());
                c.setTrainer(classDTO.getTrainer());
                c.setDay(classDTO.getDay());
                c.setTime(classDTO.getTime());
                c.setCapacity(classDTO.getCapacity());
                // Update status based on enrolled
                c.setStatus(c.getEnrolled() >= c.getCapacity() ? "FULL" : "AVAILABLE");
                classRepository.save(c);
                return VarList.Created;
            }).orElse(VarList.Not_Found);
        }

        @Override
        public int deleteClass(Long id) {
            return classRepository.findById(id).map(c -> {
                classRepository.delete(c);
                return VarList.Created;
            }).orElse(VarList.Not_Found);
        }

        @Override
        public List<ClassDTO> getAllClasses() {
            return classRepository.findAll().stream().map(c -> new ClassDTO(
                    c.getId(), c.getName(), c.getTrainer(), c.getDay(),
                    c.getTime(), c.getCapacity(), c.getEnrolled(), c.getStatus()
            )).collect(Collectors.toList());
        }

        @Override
        public List<ClassDTO> getAvailableClasses() {
            return classRepository.findAll().stream()
                    .filter(c -> c.getEnrolled() < c.getCapacity())
                    .map(c -> new ClassDTO(
                            c.getId(), c.getName(), c.getTrainer(), c.getDay(),
                            c.getTime(), c.getCapacity(), c.getEnrolled(), c.getStatus()
                    )).collect(Collectors.toList());
        }



    @Override
    public int getTotalClassCount() {
        return 0;
    }
}
