package com.tucine.cineclubadministration.Cineclub.dto.normal;

import com.tucine.cineclubadministration.Cineclub.model.CineclubType;
import com.tucine.cineclubadministration.Film.model.Film;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class CineclubDto {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private Date openInHours;
    private String socialReason;
    private String RUC;
    private String logoSrc;
    private String bannerSrc;
    private String state;
    private String description;
    private Integer capacity;

    private CineclubType cineclubType;
    public List<Film> films;
}
