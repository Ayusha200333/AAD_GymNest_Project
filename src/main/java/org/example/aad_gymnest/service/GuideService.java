package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.GuideDTO;

import java.util.List;

public interface GuideService {
    int saveGuide(GuideDTO guideDTO);
    int updateGuide(String email, GuideDTO guideDTO);
    int deactivateGuide(String email);
    int activateGuide(String email);
    List<GuideDTO> getAllGuides();
    List<GuideDTO> getAvailableGuides();
    int getTotalGuideCount();

    int deleteGuide(String email);
}



