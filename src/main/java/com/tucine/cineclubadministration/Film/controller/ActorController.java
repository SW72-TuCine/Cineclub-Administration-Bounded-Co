package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.ActorDto;
import com.tucine.cineclubadministration.Film.dto.receive.ActorReceiveDto;
import com.tucine.cineclubadministration.Film.service.interf.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/v1")
public class ActorController {

    @Autowired
    private ActorService actorService;

    //URL: http://localhost:8080/api/TuCine/v1/actors
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/actors")
    public ResponseEntity<List<ActorDto>>getAllActors(){
        return new ResponseEntity<>(actorService.getAllActors(), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/actors
    //Method: POST
    @Transactional
    @PostMapping("/actors")
    public ResponseEntity<ActorDto> createActor(@RequestBody ActorReceiveDto actorReceiveDto){
        return new ResponseEntity<>(actorService.createActor(actorReceiveDto), org.springframework.http.HttpStatus.CREATED);
    }

    //URL : http://localhost:8080/api/TuCine/v1/actors/{actorId}
    //Method:PUT
    @Transactional
    @PutMapping("/actors/{actorId}")
    public ResponseEntity<ActorDto> modifyActor(@PathVariable Long actorId, @RequestBody ActorReceiveDto updatedActor){
        return new ResponseEntity<>(actorService.modifyActor(actorId, updatedActor), org.springframework.http.HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/actors/{actorId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/actors/{actorId}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long actorId){
        actorService.deleteActor(actorId);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }
}
