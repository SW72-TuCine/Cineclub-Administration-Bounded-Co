package com.tucine.cineclubadministration.Film.service.impl;

import com.tucine.cineclubadministration.Film.dto.normal.CategoryDto;
import com.tucine.cineclubadministration.Film.dto.receive.CategoryReceiveDto;
import com.tucine.cineclubadministration.Film.model.Category;
import com.tucine.cineclubadministration.Film.repository.CategoryRepository;
import com.tucine.cineclubadministration.Film.service.interf.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    CategoryServiceImpl(){
        modelMapper = new ModelMapper();
    }

    public CategoryDto EntityToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category DtoToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(CategoryReceiveDto categoryReceiveDto) {

        CategoryDto categoryDto = modelMapper.map(categoryReceiveDto,CategoryDto.class);

        validateCategory(categoryDto);
        existCategoryByName(categoryDto.getName());

        Category category = DtoToEntity(categoryDto);

        return EntityToDto(categoryRepository.save(category));


    }

    @Override
    public CategoryDto modifyCategory(Long categoryId, CategoryReceiveDto updatedCategory) {
        // Primero, verificamos si la categoría existe en la base de datos
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        // Actualizamos los campos de la categoría existente con los valores proporcionados
        existingCategory.setName(updatedCategory.getName());

        // Guardamos la categoría actualizada en la base de datos y la devolvemos como DTO
        return EntityToDto(categoryRepository.save(existingCategory));
    }


    @Override
    public void deleteCategory(Long categoryId) {
        // Verificamos si la categoría existe en la base de datos
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        // Eliminamos la categoría de la base de datos
        categoryRepository.delete(existingCategory);
    }
    private void validateCategory(CategoryDto category) {
        if(category.getName().isEmpty() || category.getName() == null) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
    }

    private void existCategoryByName(String name) {
        if(categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }
    }
}
