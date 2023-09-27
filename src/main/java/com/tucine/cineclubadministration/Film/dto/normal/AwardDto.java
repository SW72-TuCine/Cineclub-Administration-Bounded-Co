package com.tucine.cineclubadministration.Film.dto.normal;

import com.tucine.cineclubadministration.Film.model.Film;
import lombok.Data;

import java.util.List;

@Data
public class AwardDto {
    private Long id;
    private String name;
    private List<Film> films;
}
