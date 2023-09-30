package com.tucine.cineclubadministration.Film.service.impl;

import com.tucine.cineclubadministration.Film.dto.normal.ContentRatingDto;
import com.tucine.cineclubadministration.Film.dto.receive.ContentRatingReceiveDto;
import com.tucine.cineclubadministration.Film.model.ContentRating;
import com.tucine.cineclubadministration.Film.repository.ContentRatingRepository;
import com.tucine.cineclubadministration.Film.service.interf.ContentRatingService;
import com.tucine.cineclubadministration.shared.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentRatingServiceImpl implements ContentRatingService {

    @Autowired
    private ContentRatingRepository contentRatingRepository;

    @Autowired
    private ModelMapper modelMapper;

    ContentRatingServiceImpl(){
        modelMapper=new ModelMapper();
    }

    public ContentRatingDto EntityToDto(ContentRating contentRating){
        return modelMapper.map(contentRating, ContentRatingDto.class);
    }

    public ContentRating DtoToEntity(ContentRatingDto contentRatingDto){
        return modelMapper.map(contentRatingDto, ContentRating.class);
    }


    @Override
    public List<ContentRatingDto> getAllContentRatings() {
        List<ContentRating> contentRatings=contentRatingRepository.findAll();
        return contentRatings.stream()
                .map(this::EntityToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ContentRatingDto createContentRating(ContentRatingReceiveDto contentRatingReceiveDto) {

        validateContentRating(contentRatingReceiveDto);
        existContentRatingByName(contentRatingReceiveDto.getName());

        ContentRatingDto contentRatingDto=modelMapper.map(contentRatingReceiveDto, ContentRatingDto.class);

        ContentRating contentRating=DtoToEntity(contentRatingDto);

        return EntityToDto(contentRatingRepository.save(contentRating));
    }

    @Override
    public ContentRatingDto modifyContentRating(Long contentRatingId, ContentRatingReceiveDto contentRatingReceiveDto) {
        // Buscar la clasificación por su ID
        ContentRating existingContentRating = contentRatingRepository.findById(contentRatingId)
                .orElseThrow(() -> new ValidationException("No se encontró una clasificación con el ID proporcionado"));

        // Validar y asignar los nuevos valores
        validateContentRating(contentRatingReceiveDto);
        existingContentRating.setName(contentRatingReceiveDto.getName());
        existingContentRating.setDescription(contentRatingReceiveDto.getDescription());

        // Guardar y devolver la clasificación modificada
        return EntityToDto(contentRatingRepository.save(existingContentRating));
    }

    @Override
    public void deleteContentRating(Long contentRatingId) {
        // Buscar la clasificación por su ID
        ContentRating existingContentRating = contentRatingRepository.findById(contentRatingId)
                .orElseThrow(() -> new ValidationException("No se encontró una clasificación con el ID proporcionado"));

        // Eliminar la clasificación de la base de datos
        contentRatingRepository.delete(existingContentRating);
    }
    private void validateContentRating(ContentRatingReceiveDto contentRatingReceiveDto) {
        if (contentRatingReceiveDto == null ) {
            throw new ValidationException("ContentRating no contiene nada");
        }
        if (contentRatingReceiveDto.getName() == null || contentRatingReceiveDto.getName().isEmpty()) {
            throw new ValidationException("El nombre de la clasifiación es requerido");
        }
        if(contentRatingReceiveDto.getDescription()==null || contentRatingReceiveDto.getDescription().isEmpty()){
            throw new ValidationException("La descripción de la clasificación es requerida");
        }
    }

    private void existContentRatingByName(String name){
        if(contentRatingRepository.existsByName(name)){
            throw new ValidationException("Ya existe una clasificación con ese nombre");
        }
    }
}
