package org.example.aad_gymnest.service.impl;

import jakarta.transaction.Transactional;
import org.example.aad_gymnest.dto.MembershipDTO;
import org.example.aad_gymnest.entity.LocationEntity;
import org.example.aad_gymnest.entity.MembershipEntity;
import org.example.aad_gymnest.repo.LocationRepository;
import org.example.aad_gymnest.repo.MembershipRepository;
import org.example.aad_gymnest.service.MembershipService;
import org.example.aad_gymnest.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private  MembershipRepository membershipRepository;

    @Autowired
    private  LocationRepository locationRepository;

    @Autowired
    private  ModelMapper modelMapper;

    @Override
    @Transactional
    public int saveMembership(MembershipDTO membershipDTO) {
        // check duplicate membership by name
        if (membershipRepository.existsByName(membershipDTO.getName())) {
            return VarList.Not_Acceptable;
        }

        // map DTO → Entity
        MembershipEntity membership = modelMapper.map(membershipDTO, MembershipEntity.class);

        // convert DTO string addresses → Location entities
        List<LocationEntity> locationList = membershipDTO.getAddress().stream()
                .map(addr -> {
                    LocationEntity loc = new LocationEntity();
                    loc.setName(addr);       // <<< MUST set name
                    loc.setAddress(addr);    // optional: physical address or same as name
                    loc.setOpenHours(membershipDTO.getOpenHours()); // optional
                    return locationRepository.save(loc);
                })
                .collect(Collectors.toList());

        membership.setAddress(locationList);

        membershipRepository.save(membership);
        return VarList.Created;
    }

    @Override
    @Transactional
    public int updateMembership(Long id, MembershipDTO membershipDTO) {
        Optional<MembershipEntity> existingOpt = membershipRepository.findById(id);
        if (existingOpt.isPresent()) {
            MembershipEntity existing = existingOpt.get();

            // manually map fields instead of full ModelMapper
            existing.setName(membershipDTO.getName());
            existing.setDescription(membershipDTO.getDescription());
            existing.setPrice(membershipDTO.getPrice());
            existing.setOpenHours(membershipDTO.getOpenHours());
            existing.setImageUrl(membershipDTO.getImageUrl());

            // update locations
            List<LocationEntity> locationList = membershipDTO.getAddress().stream()
                    .map(addr -> {
                        LocationEntity loc = new LocationEntity();
                        loc.setName(addr);
                        loc.setAddress(addr);
                        loc.setOpenHours(membershipDTO.getOpenHours());
                        return locationRepository.save(loc);
                    })
                    .collect(Collectors.toList());
            existing.setAddress(locationList);

            membershipRepository.save(existing);
            return VarList.Success;
        }
        return VarList.Not_Found;

    }

    @Override
    @Transactional
    public int deleteMembership(Long id) {
        Optional<MembershipEntity> existingOpt = membershipRepository.findById(id);
        if (existingOpt.isPresent()) {
            membershipRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        List<MembershipEntity> memberships = membershipRepository.findAll();

        return memberships.stream().map(membership -> {
            MembershipDTO dto = modelMapper.map(membership, MembershipDTO.class);

            // map Location entities → String addresses
            List<String> addresses = membership.getAddress().stream()
                    .map(LocationEntity::getAddress)
                    .collect(Collectors.toList());
            dto.setAddress(addresses);

            return dto;
        }).collect(Collectors.toList());
    }
}
