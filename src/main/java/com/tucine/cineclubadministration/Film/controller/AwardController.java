package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.AwardDto;
import com.tucine.cineclubadministration.Film.dto.receive.AwardReceiveDto;
import com.tucine.cineclubadministration.Film.service.interf.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/v1/cineclub_administration")
public class AwardController {
    @Autowired
    private AwardService awardService;

    //URL: http://localhost:8080/api/TuCine/v1/awards
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/awards")
    public ResponseEntity<List<AwardDto>> getAllAwards(){
        return new ResponseEntity<>(awardService.getAllAwards(), org.springframework.http.HttpStatus.OK);
    }

    //URl: http://localhost:8080/api/TuCine/v1/awards
    //Method: POST
    @Transactional
    @PostMapping("/awards")
    public ResponseEntity<AwardDto> createAward(@RequestBody AwardReceiveDto awardReceiveDto){
        return new ResponseEntity<>(awardService.createAward(awardReceiveDto), org.springframework.http.HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/awards/{awardId}
    //Method: PUT
    @Transactional
    @PutMapping("/awards/{awardId}")
    public ResponseEntity<AwardDto> modifyAward(@PathVariable Long awardId, @RequestBody AwardReceiveDto updatedAward){
        return new ResponseEntity<>(awardService.modifyAward(awardId, updatedAward), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/awards/{awardId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/awards/{awardId}")
    public ResponseEntity<Void> deleteAward(@PathVariable Long awardId){
        awardService.deleteAward(awardId);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }




}
