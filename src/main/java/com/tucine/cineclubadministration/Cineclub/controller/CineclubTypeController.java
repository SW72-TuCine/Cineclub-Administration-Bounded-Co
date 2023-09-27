package com.tucine.cineclubadministration.Cineclub.controller;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubTypeDto;
import com.tucine.cineclubadministration.Cineclub.service.interf.CineclubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/v1/cineclub_administration")
public class CineclubTypeController {

    @Autowired
    private CineclubTypeService cineclubTypeService;

    //URL: http://localhost:8080/api/TuCine/v1/cineclubTypes
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cineclubTypes")
    public ResponseEntity<List<CineclubTypeDto>> getAllCineclubTypes(){
        return new ResponseEntity<>(cineclubTypeService.getAllCineclubTypes(), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclubTypes
    //Method: POST
    @Transactional
    @PostMapping("/cineclubTypes")
    public ResponseEntity<CineclubTypeDto> createCineclubType(@RequestBody CineclubTypeDto cineclubTypeDto){
        return new ResponseEntity<>(cineclubTypeService.createCineclubType(cineclubTypeDto), org.springframework.http.HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclubTypes/{cineclubTypeId}
    //Method: PUT
    @Transactional
    @PutMapping("/cineclubTypes/{cineclubTypeId}")
    public ResponseEntity<CineclubTypeDto> modifyCineclubType(@PathVariable Long cineclubTypeId, @RequestBody CineclubTypeDto updatedCineclubType){
        return new ResponseEntity<>(cineclubTypeService.modifyCineclubType(cineclubTypeId, updatedCineclubType), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclubTypes/{cineclubTypeId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/cineclubTypes/{cineclubTypeId}")
    public ResponseEntity<Void> deleteCineclubType(@PathVariable Long cineclubTypeId){
        cineclubTypeService.deleteCineclubType(cineclubTypeId);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }
}
