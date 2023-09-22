package com.tucine.cineclubadministration.Cineclub.service.interf;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubDto;
import com.tucine.cineclubadministration.Cineclub.dto.receive.CineclubReceiveDto;
import com.tucine.cineclubadministration.Cineclub.model.Cineclub;

import java.util.List;

public interface CineclubService {

    List<CineclubDto> getAllCineclubs();
    CineclubDto createCineclub(CineclubReceiveDto cineclubReceiveDto);
    CineclubDto modifyCineclub(Long cineclubId, CineclubReceiveDto cineclubReceiveDto);

    void deleteCineclub(Long cineclubId);

    CineclubDto addMovieToCineclub(Long cineclubId, Long movieId);

    CineclubDto removeMovieToCineclub(Long cineclubId, Long movieId);

    CineclubDto getCineclubById(Long cineclubId);

    CineclubDto getCineclubByName(String cineclubName);

}
