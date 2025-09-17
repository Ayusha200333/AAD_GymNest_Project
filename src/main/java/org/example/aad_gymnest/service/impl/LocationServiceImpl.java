package org.example.aad_gymnest.service.impl;

import jakarta.transaction.Transactional;
import org.example.aad_gymnest.dto.LocationDTO;
import org.example.aad_gymnest.entity.LocationEntity;
import org.example.aad_gymnest.repo.LocationRepository;
import org.example.aad_gymnest.service.LocationService;
import org.example.aad_gymnest.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveLocation(LocationDTO locationDTO) {
        if (locationRepository.existsByName(locationDTO.getName())) {
            return VarList.Not_Acceptable;
        }
        LocationEntity location = modelMapper.map(locationDTO, LocationEntity.class);
        locationRepository.save(location);
        return VarList.Created;
    }

    @Override
    public int updateLocation(Long id, LocationDTO locationDTO) {
        Optional<LocationEntity> existingLocationOpt = locationRepository.findById(id);
        if (existingLocationOpt.isPresent()) {
            LocationEntity existingLocation = existingLocationOpt.get();

            existingLocation.setName(locationDTO.getName());
            existingLocation.setDescription(locationDTO.getDescription());
            existingLocation.setOpenHours(locationDTO.getOpenHours());
            existingLocation.setAddress(locationDTO.getAddress());

            if (locationDTO.getImageUrl() != null) {
                existingLocation.setImageUrl(locationDTO.getImageUrl());
            }

            locationRepository.save(existingLocation);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Transactional
    @Override
    public int deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }




    @Override
    public int getTotalLocationCount() {
        return 0;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<LocationEntity> locations = locationRepository.findAll();
        return locations.stream()
                .map(loc -> modelMapper.map(loc, LocationDTO.class))
                .collect(Collectors.toList());
    }
}
