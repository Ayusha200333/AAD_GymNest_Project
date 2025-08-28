package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.MembershipDTO;

import java.util.List;

public interface MembershipService {
    int saveMembership(MembershipDTO membershipDTO);
    int updateMembership(Long id, MembershipDTO membershipDTO);
    int deleteMembership(Long id);
    List<MembershipDTO> getAllMemberships();
}
