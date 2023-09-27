package com.tucine.cineclubadministration.Film.dto.normal;

import com.tucine.cineclubadministration.Film.model.Film;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String biography;
    private String photoSrc;
    private List<Film> films;
}
