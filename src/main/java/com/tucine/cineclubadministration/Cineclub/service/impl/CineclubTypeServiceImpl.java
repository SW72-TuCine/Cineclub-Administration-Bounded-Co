package com.tucine.cineclubadministration.Cineclub.service.impl;

import com.tucine.cineclubadministration.Cineclub.dto.normal.CineclubTypeDto;
import com.tucine.cineclubadministration.Cineclub.model.CineclubType;
import com.tucine.cineclubadministration.Cineclub.repository.CineclubTypeRepository;
import com.tucine.cineclubadministration.Cineclub.service.interf.CineclubTypeService;
import com.tucine.cineclubadministration.shared.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CineclubTypeServiceImpl implements CineclubTypeService {

    @Autowired
    private CineclubTypeRepository cineclubTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    CineclubTypeServiceImpl(){
        modelMapper=new ModelMapper();
    }

    public CineclubTypeDto EntityToDto(CineclubType cineclubType){
        return modelMapper.map(cineclubType, CineclubTypeDto.class);
    }

    public CineclubType DtoToEntity(CineclubTypeDto cineclubTypeDto){
        return modelMapper.map(cineclubTypeDto, CineclubType.class);
    }

    @Override
    public List<CineclubTypeDto> getAllCineclubTypes() {
        List<CineclubType> cineclubTypes= cineclubTypeRepository.findAll();
        return cineclubTypes.stream()
                .map(this::EntityToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public CineclubTypeDto createCineclubType(CineclubTypeDto cineclubTypeDto) {

        validateCineclubType(cineclubTypeDto);
        existCineclubTypeByName(cineclubTypeDto.getName());

        CineclubType cineclubType=DtoToEntity(cineclubTypeDto);

        return EntityToDto(cineclubTypeRepository.save(cineclubType));
    }

    private void existCineclubTypeByName(String name) {
        if(cineclubTypeRepository.existsByName(name)){
            throw new ValidationException("Ya existe un tipo de cineclub con ese nombre");
        }
    }

    private void validateCineclubType(CineclubTypeDto cineclubTypeDto) {

        if(cineclubTypeDto.getName()==null || cineclubTypeDto.getName().isEmpty()){
            throw new ValidationException("El nombre no puede ser nulo o vacío");
        }
    }

    @Override
    public CineclubTypeDto modifyCineclubType(Long cineclubTypeId, CineclubTypeDto cineclubTypeDto) {

        CineclubType cineclubType=cineclubTypeRepository.findById(cineclubTypeId)
                .orElseThrow(()->new ValidationException("No se encontró un tipo de cineclub con el ID proporcionado"));

        validateCineclubType(cineclubTypeDto);
        cineclubType.setName(cineclubTypeDto.getName());

        return EntityToDto(cineclubTypeRepository.save(cineclubType));
    }

    @Override
    public void deleteCineclubType(Long cineclubTypeId) {

        CineclubType cineclubType=cineclubTypeRepository.findById(cineclubTypeId)
                .orElseThrow(()->new ValidationException("No se encontró un tipo de cineclub con el ID proporcionado"));

        cineclubTypeRepository.delete(cineclubType);
    }
}
