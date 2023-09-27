package com.tucine.cineclubadministration.Film.service.impl;

import com.tucine.cineclubadministration.Film.dto.normal.ActorDto;
import com.tucine.cineclubadministration.Film.dto.receive.ActorReceiveDto;
import com.tucine.cineclubadministration.Film.model.Actor;
import com.tucine.cineclubadministration.Film.repository.ActorRepository;
import com.tucine.cineclubadministration.Film.service.interf.ActorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ModelMapper modelMapper;

    ActorServiceImpl(){
        modelMapper=new ModelMapper();
    }

    public ActorDto EntityToDto(Actor actor){
        return modelMapper.map(actor, ActorDto.class);
    }

    public Actor DtoToEntity(ActorDto actorDto){
        return modelMapper.map(actorDto, Actor.class);
    }

    @Override
    public List<ActorDto> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        return actors.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActorDto createActor(ActorReceiveDto actorReceiveDto) {

        validateActor(actorReceiveDto);
        existActorByFirstName(actorReceiveDto.getFirstName(),actorReceiveDto.getLastName());

        ActorDto actorDto=modelMapper.map(actorReceiveDto, ActorDto.class);

        Actor actor = DtoToEntity(actorDto);

        return EntityToDto(actorRepository.save(actor));
    }

    @Override
    public ActorDto modifyActor(Long actorId, ActorReceiveDto actorReceiveDto) {
        // Buscar el actor existente por su ID
        Actor existingActor = actorRepository.findById(actorId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un actor con el ID proporcionado"));

        // Actualizar los campos del actor con los datos de ActorReceiveDto
        existingActor.setFirstName(actorReceiveDto.getFirstName());
        existingActor.setLastName(actorReceiveDto.getLastName());
        existingActor.setBirthdate(actorReceiveDto.getBirthdate());
        existingActor.setBiography(actorReceiveDto.getBiography());
        existingActor.setPhotoSrc(actorReceiveDto.getPhotoSrc());

        // Guardar el actor actualizado en la base de datos
        Actor updatedActor = actorRepository.save(existingActor);

        // Devolver el actor actualizado como ActorDto
        return EntityToDto(updatedActor);
    }
    @Override
    public void deleteActor(Long actorId) {
        // Buscar el actor por su ID
        Actor actorToDelete = actorRepository.findById(actorId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un actor con el ID proporcionado"));

        // Eliminar el actor de la base de datos
        actorRepository.delete(actorToDelete);
    }

    private void validateActor(ActorReceiveDto actor) {
        if (actor.getFirstName() == null || actor.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (actor.getLastName() == null || actor.getLastName().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (actor.getBirthdate() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }
    }

    private void existActorByFirstName(String firstName,String lastName){
        if (actorRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new IllegalArgumentException("Ya existe un actor con el nombre " + firstName + " " + lastName);
        }
    }

}
