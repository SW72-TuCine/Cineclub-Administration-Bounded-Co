package com.tucine.cineclubadministration.Film.service.interf;

import com.tucine.cineclubadministration.Film.dto.normal.*;
import com.tucine.cineclubadministration.Film.dto.receive.FilmReceiveDto;
import com.tucine.cineclubadministration.Film.model.ExternalMovie;
import com.tucine.cineclubadministration.Film.model.Film;

import java.util.List;

public interface FilmService{

    List<FilmDto> getAllFilms();
    FilmDto createNewFilm(FilmReceiveDto filmReceiveDto);

    ContentRatingDto getContentRatingByFilmId(Long filmId);

    List<CategoryDto> getAllCategoriesByFilmId(Long filmId);

    List<ActorDto> getAllActorsByFilmId(Long filmId);

    List<AwardDto> getAllAwardsByFilmId(Long filmId);

    List<ExternalMovie> searchFilmInExternalApi(String title);
    List<FilmDto> searchExistingFilm(String title);

    //Obtener películas por categoría
    List<FilmDto> getFilmsByCategory(String category);

    //Añadir categorías a una película
    FilmDto addCategoriesToFilmByCategoriesIds(Long filmId, List<Long> categoriesIds);

    //Añadir actores a una película
    FilmDto addActorsToFilmByActorsIds(Long filmId, List<Long> actorsIds);

    //Añadir premios a una película
    FilmDto addAwardsToFilmByAwardsIds(Long filmId, List<Long> awardsIds);

    //Añadir un cineclub a una película
    FilmDto addCineclubToFilmByCineclubId(Long filmId, Long cineclubId);

    //OBTENER TODAS LAS PELÍCULAS QUE ESTAN DENTRO DE UN CINECLUB

    FilmDto getFilmByTitle(String title);
    FilmDto getFilmById(Long filmId);

    //128c248aa05308b3052afb5d83556f6e API KEY

}
