package org.example.aad_gymnest.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.aad_gymnest.dto.MembershipDTO;
import org.example.aad_gymnest.entity.Location;
import org.example.aad_gymnest.entity.Membership;
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
        Membership membership = modelMapper.map(membershipDTO, Membership.class);

        // convert DTO string addresses → Location entities
        List<Location> locationList = membershipDTO.getAddress().stream()
                .map(addr -> locationRepository.findById(Long.parseLong(String.valueOf(addr)))
                        .orElseGet(() -> {
                            Location newLoc = new Location();
                            newLoc.setAddress(addr);
                            return locationRepository.save(newLoc);
                        }))
                .collect(Collectors.toList());

        membership.setAddress(locationList);

        membershipRepository.save(membership);
        return VarList.Created;
    }

    @Override
    @Transactional
    public int updateMembership(Long id, MembershipDTO membershipDTO) {
        Optional<Membership> existingOpt = membershipRepository.findById(id);
        if (existingOpt.isPresent()) {
            Membership existing = existingOpt.get();

            // map new values
            modelMapper.map(membershipDTO, existing);

            // update locations mapping
            List<Location> locationList = membershipDTO.getAddress().stream()
                    .map(addr -> locationRepository.findById(addr)
                            .orElseGet(() -> {
                                Location newLoc = new Location();
                                newLoc.setAddress(addr);
                                return locationRepository.save(newLoc);
                            }))
                    .collect(Collectors.toList());
            existing.setAddress(locationList);

            membershipRepository.save(existing);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    @Transactional
    public int deleteMembership(Long id) {
        Optional<Membership> existingOpt = membershipRepository.findById(id);
        if (existingOpt.isPresent()) {
            membershipRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll();

        return memberships.stream().map(membership -> {
            MembershipDTO dto = modelMapper.map(membership, MembershipDTO.class);

            // map Location entities → String addresses
            List<String> addresses = membership.getAddress().stream()
                    .map(Location::getAddress)
                    .collect(Collectors.toList());
            dto.setAddress(addresses);

            return dto;
        }).collect(Collectors.toList());
    }
}
