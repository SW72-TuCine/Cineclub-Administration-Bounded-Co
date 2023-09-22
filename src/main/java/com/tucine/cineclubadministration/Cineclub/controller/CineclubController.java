package com.tucine.cineclubadministration.Cineclub.controller;


import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubDto;
import com.tucine.cineclubadministration.Cineclub.service.interf.CineclubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/V1")
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

    // URL: http://localhost:8080/api/TuCine/V1/cineclubs/{cineclubId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cineclubs/{cineclubId}")
    public ResponseEntity<CineclubDto> getCineclubById(Long cineclubId){
        return new ResponseEntity<>(cineclubService.getCineclubById(cineclubId), org.springframework.http.HttpStatus.OK);
    }




}
