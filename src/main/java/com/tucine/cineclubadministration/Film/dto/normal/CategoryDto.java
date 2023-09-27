package com.tucine.cineclubadministration.Film.dto.normal;

import com.tucine.cineclubadministration.Film.model.Film;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private List<Film> films;
}
