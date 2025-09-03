package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.dto.GuideDTO;
import org.example.aad_gymnest.entity.GuideEntity;
import org.example.aad_gymnest.repo.GuideRepository;
import org.example.aad_gymnest.service.GuideService;
import org.example.aad_gymnest.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveGuide(GuideDTO guideDTO) {
        if (guideRepository.existsByEmail(guideDTO.getEmail())) {
            return VarList.Not_Acceptable;
        }
        GuideEntity entity = modelMapper.map(guideDTO, GuideEntity.class);
        entity.setStatus("ACTIVE");  // Ensure default status
        entity.setBooked("NO");       // Ensure default booked
        guideRepository.save(entity);
        return VarList.Created;
    }

    @Override
    public int updateGuide(String email, GuideDTO guideDTO) {
        Optional<GuideEntity> existing = guideRepository.findByEmail(email);
        if (existing.isPresent()) {
            GuideEntity entity = existing.get();
            entity.setFullName(guideDTO.getFullName());
            entity.setDescription(guideDTO.getDescription());
            entity.setPaymentPerHour(guideDTO.getPaymentPerHour());
            entity.setPhone(guideDTO.getPhone());
            if (guideDTO.getImageUrl() != null && !guideDTO.getImageUrl().isEmpty()) {
                entity.setImageUrl(guideDTO.getImageUrl());
            }
            guideRepository.save(entity);
            return VarList.Created;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int deactivateGuide(String email) {
        Optional<GuideEntity> existing = guideRepository.findByEmail(email);
        if (existing.isPresent()) {
            GuideEntity entity = existing.get();
            entity.setStatus("INACTIVE");
            guideRepository.save(entity);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public int activateGuide(String email) {
        Optional<GuideEntity> existing = guideRepository.findByEmail(email);
        if (existing.isPresent()) {
            GuideEntity entity = existing.get();
            entity.setStatus("ACTIVE");
            guideRepository.save(entity);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<GuideDTO> getAllGuides() {
        return guideRepository.findAll()
                .stream()
                .map(g -> modelMapper.map(g, GuideDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GuideDTO> getAvailableGuides() {
        return guideRepository.findAllByBookedAndStatus("NO", "ACTIVE")
                .stream()
                .map(g -> modelMapper.map(g, GuideDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalGuideCount() {
        return (int) guideRepository.count();
    }
}
