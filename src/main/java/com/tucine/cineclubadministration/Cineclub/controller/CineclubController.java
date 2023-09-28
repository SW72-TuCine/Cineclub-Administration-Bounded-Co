package com.tucine.cineclubadministration.Cineclub.controller;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubDto;
import com.tucine.cineclubadministration.Cineclub.dto.receive.CineclubReceiveDto;
import com.tucine.cineclubadministration.Cineclub.service.interf.CineclubService;
import com.tucine.cineclubadministration.Film.dto.normal.FilmDto;
import com.tucine.cineclubadministration.Film.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/v1/cineclub_administration")
public class CineclubController {

    @Autowired
    private CineclubService cineclubService;

    // URL: http://localhost:8080/api/TuCine/V1/cineclubs
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cineclubs")
    public ResponseEntity<List<CineclubDto>> getAllCineclubs(){
        return new ResponseEntity<>(cineclubService.getAllCineclubs(), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/
    //Method: POST
    @Transactional()
    @PostMapping("/cineclubs")
    public ResponseEntity<CineclubDto> createCineclub(CineclubReceiveDto cineclubReceiveDto){
        return new ResponseEntity<>(cineclubService.createCineclub(cineclubReceiveDto), org.springframework.http.HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cineclubs/{cineclubId}")
    public ResponseEntity<CineclubDto> getCineclubById(Long cineclubId){
        return new ResponseEntity<>(cineclubService.getCineclubById(cineclubId), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cineclubs/byName")
    public ResponseEntity<CineclubDto> getCineclubByName(String cineclubName){
        return new ResponseEntity<>(cineclubService.getCineclubByName(cineclubName), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}/films
    //Method: GET
    @Transactional()
    @GetMapping("/cineclubs/{cineclubId}/films")
    public ResponseEntity<List<FilmDto>> getAllMoviesByCineclubId(Long cineclubId){
        return new ResponseEntity<>(cineclubService.getAllMoviesByCineclubId(cineclubId), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/cineclubs/{cineclubId}")
    public ResponseEntity<Void> deleteCineclub(@PathVariable Long cineclubId){
        cineclubService.deleteCineclub(cineclubId);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}
    //Method: PUT
    @Transactional
    @PutMapping("/cineclubs/suspend/{cineclubId}")
    public ResponseEntity<CineclubDto> suspendCineclub(@PathVariable Long cineclubId){
        return new ResponseEntity<>(cineclubService.suspendCineclub(cineclubId), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}
    //Method: PUT
    @Transactional
    @PutMapping("/cineclubs/hide/{cineclubId}")
    public ResponseEntity<CineclubDto> hideCineclub(@PathVariable Long cineclubId){
        return new ResponseEntity<>(cineclubService.hideCineclub(cineclubId), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/cineclubs/{cineclubId}
    //Method: PUT
    @Transactional
    @PutMapping("/cineclubs/modify/{cineclubId}")
    public ResponseEntity<CineclubDto> modifyCineclub(@PathVariable Long cineclubId, @RequestBody CineclubReceiveDto cineclubReceiveDto){
        return new ResponseEntity<>(cineclubService.modifyCineclub(cineclubId, cineclubReceiveDto), org.springframework.http.HttpStatus.OK);
    }



}
