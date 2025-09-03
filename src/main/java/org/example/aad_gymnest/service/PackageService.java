package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.PackageDTO;

import java.util.List;

public interface PackageService {
    int savePackage(PackageDTO packageDTO);
    int updatePackage(Long id, PackageDTO packageDTO);
    int deletePackage(Long id);
    List<PackageDTO> getAllPackages();
}
