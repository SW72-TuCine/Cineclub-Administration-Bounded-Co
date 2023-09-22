package com.tucine.cineclubadministration.Film.service.interf;


import com.tucine.cineclubadministration.Film.dto.normal.ActorDto;
import com.tucine.cineclubadministration.Film.dto.receive.ActorReceiveDto;
import com.tucine.cineclubadministration.Film.dto.receive.CategoryReceiveDto;

import java.util.List;

public interface ActorService {

    List<ActorDto> getAllActors();
    ActorDto createActor(ActorReceiveDto actorDto);
    ActorDto modifyActor(Long actorId, ActorReceiveDto actorReceiveDto);
    public void deleteActor(Long actorId);
}
