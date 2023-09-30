package com.tucine.cineclubadministration.Film.service.impl;

import com.tucine.cineclubadministration.Film.dto.normal.AwardDto;
import com.tucine.cineclubadministration.Film.dto.receive.AwardReceiveDto;
import com.tucine.cineclubadministration.Film.model.Award;
import com.tucine.cineclubadministration.Film.repository.AwardRepository;
import com.tucine.cineclubadministration.Film.service.interf.AwardService;
import com.tucine.cineclubadministration.shared.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private ModelMapper modelMapper;

    AwardServiceImpl(){
        modelMapper=new ModelMapper();
    }

    public AwardDto EntityToDto(Award award){
        return modelMapper.map(award, AwardDto.class);
    }

    public Award DtoToEntity(AwardDto awardDto){
        return modelMapper.map(awardDto, Award.class);
    }


    @Override
    public List<AwardDto> getAllAwards() {
        List<Award> awards = awardRepository.findAll();
        return awards.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());

    }

    @Override
    public AwardDto createAward(AwardReceiveDto awardReceiveDto) {

        validateAward(awardReceiveDto);
        existAward(awardReceiveDto.getName());

        AwardDto awardDto=modelMapper.map(awardReceiveDto, AwardDto.class);

        Award award = DtoToEntity(awardDto);

        return EntityToDto(awardRepository.save(award));
    }

    @Override
    public AwardDto modifyAward(Long awardId, AwardReceiveDto awardReceiveDto) {
        // Buscar el premio por su ID
        Award existingAward = awardRepository.findById(awardId)
                .orElseThrow(() -> new ValidationException("No se encontró un premio con el ID proporcionado"));

        // Validar y actualizar los datos del premio
        validateAward(awardReceiveDto);
        existingAward.setName(awardReceiveDto.getName()); // Actualizamos el nombre

        // Guardar los cambios en la base de datos
        Award updatedAward = awardRepository.save(existingAward);

        // Devolver el premio modificado
        return EntityToDto(updatedAward);
    }

    @Override
    public void deleteAward(Long awardId) {
        // Buscar el premio por su ID
        Award existingAward = awardRepository.findById(awardId)
                .orElseThrow(() -> new ValidationException("No se encontró un premio con el ID proporcionado"));

        // Eliminar el premio de la base de datos
        awardRepository.delete(existingAward);
    }

    private void validateAward(AwardReceiveDto award){
        if(award.getName()==null || award.getName().isEmpty()){
            throw new ValidationException("El nombre del premio no puede estar vacio");
        }
    }

    private void existAward(String name){
        if(awardRepository.existsByName(name)){
            throw new ValidationException("Ya existe un premio con ese nombre");
        }
    }
}
