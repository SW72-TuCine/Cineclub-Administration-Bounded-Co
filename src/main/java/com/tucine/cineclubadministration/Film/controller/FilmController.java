package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.FilmDto;
import com.tucine.cineclubadministration.Film.service.interf.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    //URL: http://localhost:8080/api/TuCine/V1/cineclub_administration/films/search?title=blue
    @Transactional(readOnly = true)
    @GetMapping("/films/search")
    public ResponseEntity<List<FilmDto>> searchExistingFilm(@RequestParam("title") String title){
        List<FilmDto> films = filmService.searchExistingFilm(title);
        return ResponseEntity.ok(films);
    }


}
