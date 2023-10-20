package com.tucine.cineclubadministration.Cineclub.service.interf;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubDto;
import com.tucine.cineclubadministration.Cineclub.dto.receive.CineclubReceiveDto;
import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Film.dto.normal.FilmDto;

import java.util.List;

public interface CineclubService {

    List<CineclubDto> getAllCineclubs();
    CineclubDto createCineclub(CineclubReceiveDto cineclubReceiveDto);
    CineclubDto modifyCineclub(Long cineclubId, CineclubReceiveDto cineclubReceiveDto);

    void deleteCineclub(Long cineclubId);
    List<FilmDto> getAllMoviesByCineclubId(Long cineclubId);

    List<CineclubDto> getCineclubByCategorieName(String categorie);

    CineclubDto addMovieToCineclub(Long cineclubId, Long movieId);

    CineclubDto removeMovieToCineclub(Long cineclubId, Long movieId);

    CineclubDto suspendCineclub(Long cineclubId);

    CineclubDto hideCineclub(Long cineclubId);

    CineclubDto getCineclubById(Long cineclubId);

    List<CineclubDto> getCineclubByName(String cineclubName);

}
