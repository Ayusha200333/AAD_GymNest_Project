package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.LocationDTO;
import java.util.List;

public interface LocationService {
    int saveLocation(LocationDTO locationDTO);
    List<LocationDTO> getAllLocations();
    int updateLocation(Long id, LocationDTO locationDTO);
    int deleteLocation(Long id);



    int getTotalLocationCount();
}
