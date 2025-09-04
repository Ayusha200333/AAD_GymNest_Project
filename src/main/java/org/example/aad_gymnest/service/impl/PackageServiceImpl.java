package org.example.aad_gymnest.service.impl;

import jakarta.transaction.Transactional;
import org.example.aad_gymnest.dto.PackageDTO;
import org.example.aad_gymnest.entity.ClassEntity;
import org.example.aad_gymnest.entity.PackageEntity;
import org.example.aad_gymnest.repo.ClassRepository;
import org.example.aad_gymnest.repo.PackageRepository;
import org.example.aad_gymnest.service.PackageService;
import org.example.aad_gymnest.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public int savePackage(PackageDTO packageDTO) {
        if (packageRepository.existsByName(packageDTO.getName())) {
            return VarList.Not_Acceptable;
        }

        PackageEntity packageEntity = modelMapper.map(packageDTO, PackageEntity.class);

        // Map class names/IDs → ClassEntity
        if (packageDTO.getClasses() != null && !packageDTO.getClasses().isEmpty()) {
            List<ClassEntity> classList = packageDTO.getClasses().stream()
                    .map(name -> classRepository.findByName(name)
                            .orElseThrow(() -> new RuntimeException("Class not found: " + name)))
                    .collect(Collectors.toList());
            packageEntity.setClasses(classList);
        }

        packageRepository.save(packageEntity);
        return VarList.Created;
    }

    @Override
    public List<PackageDTO> getAllPackages() {
        List<PackageEntity> packages = packageRepository.findAll();

        return packages.stream().map(pkg -> {
            PackageDTO dto = new PackageDTO();
            dto.setId(pkg.getId());
            dto.setName(pkg.getName());
            dto.setDescription(pkg.getDescription());
            dto.setPrice(pkg.getPrice());
            dto.setOpenHours(pkg.getOpenHours());
            dto.setImageUrl(pkg.getImageUrl());

            if (pkg.getClasses() != null) {
                List<String> classNames = pkg.getClasses()
                        .stream()
                        .map(ClassEntity::getName)
                        .collect(Collectors.toList());
                dto.setClasses(classNames);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int updatePackage(Long id, PackageDTO packageDTO) {
        Optional<PackageEntity> packageOpt = packageRepository.findById(id);
        if (packageOpt.isPresent()) {
            PackageEntity packageEntity = packageOpt.get();

            packageEntity.setName(packageDTO.getName());
            packageEntity.setDescription(packageDTO.getDescription());
            packageEntity.setPrice(packageDTO.getPrice());
            packageEntity.setOpenHours(packageDTO.getOpenHours());
            packageEntity.setImageUrl(packageDTO.getImageUrl());

            if (packageDTO.getClasses() != null && !packageDTO.getClasses().isEmpty()) {
                List<ClassEntity> classList = packageDTO.getClasses().stream()
                        .map(name -> classRepository.findByName(name)
                                .orElseThrow(() -> new RuntimeException("Class not found: " + name)))
                        .collect(Collectors.toList());
                packageEntity.setClasses(classList);
            } else {
                packageEntity.setClasses(null);
            }

            packageRepository.save(packageEntity);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    @Transactional
    public int deletePackage(Long id) {
        if (packageRepository.existsById(id)) {
            packageRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }
}
