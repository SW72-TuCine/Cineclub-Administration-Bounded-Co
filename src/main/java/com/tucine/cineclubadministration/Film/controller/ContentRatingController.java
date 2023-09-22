package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.ContentRatingDto;
import com.tucine.cineclubadministration.Film.dto.receive.ContentRatingReceiveDto;
import com.tucine.cineclubadministration.Film.service.interf.ContentRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/TuCine/v1")
public class ContentRatingController {

    @Autowired
    private ContentRatingService contentRatingService;

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/contentRatings")
    public ResponseEntity<List<ContentRatingDto>> getAllContentRatings(){
        return new ResponseEntity<>(contentRatingService.getAllContentRatings(), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings
    //Method: POST
    @Transactional
    @PostMapping("/contentRatings")
    public ResponseEntity<ContentRatingDto> createContentRating(@RequestBody ContentRatingReceiveDto contentRatingReceiveDto){
        return new ResponseEntity<>(contentRatingService.createContentRating(contentRatingReceiveDto), org.springframework.http.HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings/{contentRatingId}
    //Method: PUT
    @Transactional
    @PutMapping("/contentRatings/{contentRatingId}")
    public ResponseEntity<ContentRatingDto> modifyContentRating(@PathVariable Long contentRatingId, @RequestBody ContentRatingReceiveDto updatedContentRating){
        return new ResponseEntity<>(contentRatingService.modifyContentRating(contentRatingId, updatedContentRating), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings/{contentRatingId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/contentRatings/{contentRatingId}")
    public ResponseEntity<Void> deleteContentRating(@PathVariable Long contentRatingId){
        contentRatingService.deleteContentRating(contentRatingId);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }


}
