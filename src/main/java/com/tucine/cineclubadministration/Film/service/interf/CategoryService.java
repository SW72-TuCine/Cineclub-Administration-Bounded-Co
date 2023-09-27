package com.tucine.cineclubadministration.Film.service.interf;

import com.tucine.cineclubadministration.Film.dto.normal.CategoryDto;
import com.tucine.cineclubadministration.Film.dto.receive.CategoryReceiveDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();
    CategoryDto createCategory(CategoryReceiveDto categoryReceiveDto);
    CategoryDto modifyCategory(Long categoryId, CategoryReceiveDto categoryReceiveDto);
    public void deleteCategory(Long categoryId);

}
