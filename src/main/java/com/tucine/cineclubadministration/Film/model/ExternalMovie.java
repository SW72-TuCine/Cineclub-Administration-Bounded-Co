package com.tucine.cineclubadministration.Film.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalMovie {
    private int id;
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private List<String> genres;
}
