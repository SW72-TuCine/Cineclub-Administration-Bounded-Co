package com.tucine.cineclubadministration.Film.model;

import lombok.Data;

import java.util.List;

@Data
public class ExternalMovie {
    private int id;
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private List<String> genres;
}
