package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.FilmDto;
import com.tucine.cineclubadministration.Film.service.interf.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/v1/cineclub_administration")
public class FilmController {
    @Autowired
    private FilmService filmService;


    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films
    @Transactional(readOnly = true)
    @GetMapping("/films")
    public ResponseEntity<List<FilmDto>> getAllFilms(){
        List<FilmDto> listFilmDto = filmService.getAllFilms();
        return ResponseEntity.ok(listFilmDto);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/{filmId}
    @Transactional
    @GetMapping("/films/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable("id") Long filmId){
        FilmDto filmDto = filmService.getFilmById(filmId);
        return ResponseEntity.ok(filmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/{category}
    @Transactional(readOnly = true)
    @GetMapping("/films/{category}")
    public ResponseEntity<List<FilmDto>> getFilmsByCategory(@PathVariable("category") String category){
        List<FilmDto> listFilmDto = filmService.getFilmsByCategory(category);
        return ResponseEntity.ok(listFilmDto);
    }


}
