package com.tucine.cineclubadministration.Cineclub.service.impl;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubDto;
import com.tucine.cineclubadministration.Cineclub.dto.receive.CineclubReceiveDto;
import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Cineclub.repository.CineclubRepository;
import com.tucine.cineclubadministration.Cineclub.service.interf.CineclubService;
import com.tucine.cineclubadministration.Film.model.Film;
import com.tucine.cineclubadministration.Film.repository.FilmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CineclubServiceImpl implements CineclubService {

    @Autowired
    private CineclubRepository cineclubRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ModelMapper modelMapper;

    CineclubServiceImpl(){
        modelMapper=new ModelMapper();
    }

    public CineclubDto EntityToDto(Cineclub cineclub){
        return modelMapper.map(cineclub, CineclubDto.class);
    }

    public Cineclub DtoToEntity(CineclubDto cineclubDto){
        return modelMapper.map(cineclubDto, Cineclub.class);
    }

    @Override
    public List<CineclubDto> getAllCineclubs() {
        List<Cineclub> cineclubs = cineclubRepository.findAll();
        return cineclubs.stream().map(this::EntityToDto).toList();
    }

    @Override
    public CineclubDto createCineclub(CineclubReceiveDto cineclubReceiveDto) {

        validateCineclub(cineclubReceiveDto);
        existCineclubByName(cineclubReceiveDto.getName());

        CineclubDto cineclubDto=modelMapper.map(cineclubReceiveDto, CineclubDto.class);

        Cineclub cineclub=DtoToEntity(cineclubDto);

        return EntityToDto(cineclubRepository.save(cineclub));
    }

    private void existCineclubByName(String name) {
        if(cineclubRepository.existsByName(name)){
            throw new RuntimeException("Ya existe un cineclub con ese nombre");
        }
    }

    private void validateCineclub(CineclubReceiveDto cineclubReceiveDto) {

        if(cineclubReceiveDto.getName()==null || cineclubReceiveDto.getName().isEmpty()){
            throw new RuntimeException("El nombre del cineclub no puede estar vacio");
        }
        if(cineclubReceiveDto.getCapacity()==null || cineclubReceiveDto.getCapacity()<0){
            throw new RuntimeException("La capacidad del cineclub no puede ser nula o negativa");
        }
        if(cineclubReceiveDto.getAddress()==null || cineclubReceiveDto.getAddress().isEmpty()){
            throw new RuntimeException("La dirección del cineclub no puede estar vacia");
        }

    }

    @Override
    public CineclubDto modifyCineclub(Long cineclubId, CineclubReceiveDto cineclubReceiveDto) {
        Cineclub existingCineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        validateCineclub(cineclubReceiveDto);

        // Verifica y actualiza los campos no vacíos en cineclubReceiveDto
        if (cineclubReceiveDto.getName() != null && !cineclubReceiveDto.getName().isEmpty()) {
            existingCineclub.setName(cineclubReceiveDto.getName());
        }
        if (cineclubReceiveDto.getCapacity() != null) {
            existingCineclub.setCapacity(cineclubReceiveDto.getCapacity());
        }
        if (cineclubReceiveDto.getAddress() != null && !cineclubReceiveDto.getAddress().isEmpty()) {
            existingCineclub.setAddress(cineclubReceiveDto.getAddress());
        }
        if (cineclubReceiveDto.getBannerSrc() != null && !cineclubReceiveDto.getBannerSrc().isEmpty()) {
            existingCineclub.setBannerSrc(cineclubReceiveDto.getBannerSrc());
        }
        if (cineclubReceiveDto.getLogoSrc() != null && !cineclubReceiveDto.getLogoSrc().isEmpty()) {
            existingCineclub.setLogoSrc(cineclubReceiveDto.getLogoSrc());
        }
        if (cineclubReceiveDto.getDescription() != null && !cineclubReceiveDto.getDescription().isEmpty()) {
            existingCineclub.setDescription(cineclubReceiveDto.getDescription());
        }
        if (cineclubReceiveDto.getSocialReason() != null && !cineclubReceiveDto.getSocialReason().isEmpty()) {
            existingCineclub.setSocialReason(cineclubReceiveDto.getSocialReason());
        }
        if (cineclubReceiveDto.getRUC() != null && !cineclubReceiveDto.getRUC().isEmpty()) {
            existingCineclub.setRUC(cineclubReceiveDto.getRUC());
        }
        if (cineclubReceiveDto.getPhone() != null && !cineclubReceiveDto.getPhone().isEmpty()) {
            existingCineclub.setPhone(cineclubReceiveDto.getPhone());
        }
        if (cineclubReceiveDto.getFilms() != null && !cineclubReceiveDto.getFilms().isEmpty()) {
            existingCineclub.setFilms(cineclubReceiveDto.getFilms());
        }
        if (cineclubReceiveDto.getOpenInHours() != null && !cineclubReceiveDto.getOpenInHours().toString().isEmpty()) {
            existingCineclub.setOpenInHours(cineclubReceiveDto.getOpenInHours());
        }
        if (cineclubReceiveDto.getCineclubType() != null) {
            existingCineclub.setCineclubType(cineclubReceiveDto.getCineclubType());
        }
        if (cineclubReceiveDto.getState() != null) {
            existingCineclub.setState(cineclubReceiveDto.getState());
        }
        if (cineclubReceiveDto.getDescription() != null && !cineclubReceiveDto.getDescription().isEmpty()) {
            existingCineclub.setDescription(cineclubReceiveDto.getDescription());
        }

        Cineclub updatedCineclub = cineclubRepository.save(existingCineclub);

        return EntityToDto(updatedCineclub);
    }

    @Override
    public void deleteCineclub(Long cineclubId) {

        Cineclub existingCineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        cineclubRepository.delete(existingCineclub);

    }

    @Override
    public CineclubDto addMovieToCineclub(Long cineclubId, Long movieId) {
        Cineclub existingCineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        // Buscar la película por su ID
        Film existingFilm = filmRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una película con el ID proporcionado"));

        // Agregar la película al cineclub
        existingCineclub.getFilms().add(existingFilm);

        // Guardar los cambios en la base de datos
        Cineclub updatedCineclub = cineclubRepository.save(existingCineclub);

        // Devolver el cineclub modificado
        return EntityToDto(updatedCineclub);
    }

    @Override
    public CineclubDto removeMovieToCineclub(Long cineclubId, Long movieId) {
        Cineclub existingCineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        // Buscar la película por su ID
        Film existingFilm = filmRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una película con el ID proporcionado"));

        // Eliminar la película del cineclub
        existingCineclub.getFilms().remove(existingFilm);

        // Guardar los cambios en la base de datos
        Cineclub updatedCineclub = cineclubRepository.save(existingCineclub);

        // Devolver el cineclub modificado
        return EntityToDto(updatedCineclub);
    }

    @Override
    public CineclubDto getCineclubById(Long cineclubId) {
        Cineclub cineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        return EntityToDto(cineclub);
    }

    @Override
    public CineclubDto getCineclubByName(String cineclubName) {
        Cineclub cineclub = cineclubRepository.findByName(cineclubName);

        if (cineclub == null) {
            throw new IllegalArgumentException("No se encontró un cineclub con el nombre proporcionado");
        }
        return EntityToDto(cineclub);
    }
}
