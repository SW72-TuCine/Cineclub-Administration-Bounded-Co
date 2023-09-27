package com.tucine.cineclubadministration.Film.dto.normal;

import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Film.model.Actor;
import com.tucine.cineclubadministration.Film.model.Award;
import com.tucine.cineclubadministration.Film.model.Category;
import com.tucine.cineclubadministration.Film.model.ContentRating;
import lombok.Data;

import java.util.List;

@Data
public class FilmDto {

    private Long id;
    private String title;
    private String year;
    private String synopsis;
    private String posterSrc;
    private String trailerSrc;
    private int duration;
    private ContentRating contentRating;
    private List<Actor> actors;
    private List<Award> awards;
    private List<Category> categories;
    private List<Cineclub> cineclubs;
}
