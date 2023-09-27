package com.tucine.cineclubadministration.Cineclub.service.interf;


import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubTypeDto;

import java.util.List;

public interface CineclubTypeService {

    List<CineclubTypeDto> getAllCineclubTypes();
    CineclubTypeDto createCineclubType(CineclubTypeDto cineclubTypeDto);
    CineclubTypeDto modifyCineclubType(Long cineclubTypeId, CineclubTypeDto cineclubTypeDto);
    public void deleteCineclubType(Long cineclubTypeId);
}
