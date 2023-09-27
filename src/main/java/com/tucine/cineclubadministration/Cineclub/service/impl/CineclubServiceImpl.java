package com.tucine.cineclubadministration.Cineclub.service.impl;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubDto;
import com.tucine.cineclubadministration.Cineclub.dto.receive.CineclubReceiveDto;
import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Cineclub.repository.CineclubRepository;
import com.tucine.cineclubadministration.Cineclub.service.interf.CineclubService;
import com.tucine.cineclubadministration.Film.dto.normal.FilmDto;
import com.tucine.cineclubadministration.Film.model.Film;
import com.tucine.cineclubadministration.Film.repository.FilmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CineclubServiceImpl implements CineclubService {

    @Autowired
    private CineclubRepository cineclubRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<CineclubDto> getAllCineclubs() {
        List<Cineclub> cineclubs = cineclubRepository.findAll();
        return cineclubs.stream().map(this::EntityToDto).toList();
    }

    public CineclubDto EntityToDto(Cineclub cineclub){
        return modelMapper.map(cineclub, CineclubDto.class);
    }

    public Cineclub DtoToEntity(CineclubDto cineclubDto){
        return modelMapper.map(cineclubDto, Cineclub.class);
    }

    public FilmDto EntityToDto(Film film){
        return modelMapper.map(film, FilmDto.class);
    }

    @Override
    public CineclubDto createCineclub(CineclubReceiveDto cineclubReceiveDto) {

        validateCineclub(cineclubReceiveDto);
        existCineclubByName(cineclubReceiveDto.getName());

        CineclubDto cineclubDto=modelMapper.map(cineclubReceiveDto, CineclubDto.class);

        Cineclub cineclub=DtoToEntity(cineclubDto);

        return EntityToDto(cineclubRepository.save(cineclub));
    }

    @Override
    public CineclubDto modifyCineclub(Long cineclubId, CineclubReceiveDto cineclubReceiveDto) {
        return null;
    }

    @Override
    public void deleteCineclub(Long cineclubId) {
        Cineclub existingCineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        cineclubRepository.delete(existingCineclub);
    }

    @Override
    public List<FilmDto> getAllMoviesByCineclubId(Long cineclubId) {

        Cineclub cineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        List<FilmDto> filmsInOneCineclub = cineclub.getFilms().stream()
                .map(film -> EntityToDto(film)) // Utiliza un método específico para mapear Film a FilmDto
                .collect(Collectors.toList());

        return filmsInOneCineclub;
    }


    @Override
    public CineclubDto addMovieToCineclub(Long cineclubId, Long movieId) {
        return null;
    }

    @Override
    public CineclubDto removeMovieToCineclub(Long cineclubId, Long movieId) {
        return null;
    }

    @Override
    public CineclubDto suspendCineclub(Long cineclubId) {

        Cineclub cineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cineclub con el ID proporcionado"));

        if(!(cineclub.getState()=="Suspendido"))
            cineclub.setState("Suspendido");

        return EntityToDto(cineclubRepository.save(cineclub));
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
        if(cineclubReceiveDto.getOwnerId()==null){
            throw new RuntimeException("El id del dueño no puede ser nulo");
        }

    }
    private void existCineclubByName(String name) {
        if(cineclubRepository.existsByName(name)){
            throw new RuntimeException("Ya existe un cineclub con ese nombre");
        }
    }
}
