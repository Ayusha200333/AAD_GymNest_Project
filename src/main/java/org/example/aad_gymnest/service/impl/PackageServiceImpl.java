package org.example.aad_gymnest.service.impl;

import jakarta.transaction.Transactional;
import org.example.aad_gymnest.dto.PackageDTO;
import org.example.aad_gymnest.entity.LocationEntity;
import org.example.aad_gymnest.entity.PackageEntity;
import org.example.aad_gymnest.repo.LocationRepository;
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
    private  LocationRepository locationRepository;

    @Autowired
    private  ModelMapper modelMapper;

    @Override
    @Transactional
    public int savePackage(PackageDTO packageDTO) {
        // check duplicate package by name
        if (packageRepository.existsByName(packageDTO.getName())) {
            return VarList.Not_Acceptable;
        }


        PackageEntity packageEntity = modelMapper.map(packageDTO, PackageEntity.class);

        // convert DTO string addresses to Location entities
        List<LocationEntity> locationList = packageDTO.getAddress().stream()
                .map(addr -> {
                    LocationEntity loc = new LocationEntity();
                    loc.setName(addr);
                    loc.setAddress(addr);
                    loc.setOpenHours(packageDTO.getOpenHours());
                    return locationRepository.save(loc);
                })
                .collect(Collectors.toList());

        packageEntity.setAddress(locationList);

        packageRepository.save(packageEntity);
        return VarList.Created;
    }

    @Override
    @Transactional
    public int updatePackage(Long id, PackageDTO packageDTO) {
        Optional<PackageEntity> existingOpt = packageRepository.findById(id);
        if (existingOpt.isPresent()) {
            PackageEntity existing = existingOpt.get();

            // manually map fields instead of full ModelMapper
            existing.setName(packageDTO.getName());
            existing.setDescription(packageDTO.getDescription());
            existing.setPrice(packageDTO.getPrice());
            existing.setOpenHours(packageDTO.getOpenHours());
            existing.setImageUrl(packageDTO.getImageUrl());

            // update locations
            List<LocationEntity> locationList = packageDTO.getAddress().stream()
                    .map(addr -> {
                        LocationEntity loc = new LocationEntity();
                        loc.setName(addr);
                        loc.setAddress(addr);
                        loc.setOpenHours(packageDTO.getOpenHours());
                        return locationRepository.save(loc);
                    })
                    .collect(Collectors.toList());
            existing.setAddress(locationList);

            packageRepository.save(existing);
            return VarList.Created;
        }
        return VarList.Not_Found;

    }

    @Override
    @Transactional
    public int deletePackage(Long id) {
        Optional<PackageEntity> existingOpt = packageRepository.findById(id);
        if (existingOpt.isPresent()) {
            packageRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<PackageDTO> getAllPackages() {
        List<PackageEntity> packageEntities = packageRepository.findAll();

        return packageEntities.stream().map(packages -> {
            PackageDTO dto = modelMapper.map(packages, PackageDTO.class);

            // map Location entities → String addresses
            List<String> addresses = packages.getAddress().stream()
                    .map(LocationEntity::getAddress)
                    .collect(Collectors.toList());
            dto.setAddress(addresses);

            return dto;
        }).collect(Collectors.toList());
    }
}
