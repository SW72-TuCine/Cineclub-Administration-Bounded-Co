package com.tucine.cineclubadministration.Film.service.impl;

import com.tucine.cineclubadministration.Cineclub.repository.CineclubRepository;
import com.tucine.cineclubadministration.Film.dto.normal.*;
import com.tucine.cineclubadministration.Film.dto.receive.FilmReceiveDto;
import com.tucine.cineclubadministration.Film.model.Category;
import com.tucine.cineclubadministration.Film.model.ExternalMovie;
import com.tucine.cineclubadministration.Film.model.Film;
import com.tucine.cineclubadministration.Film.repository.ActorRepository;
import com.tucine.cineclubadministration.Film.repository.AwardRepository;
import com.tucine.cineclubadministration.Film.repository.CategoryRepository;
import com.tucine.cineclubadministration.Film.repository.FilmRepository;
import com.tucine.cineclubadministration.Film.service.interf.FilmService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private CineclubRepository cineclubRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        return films.stream()
                .map(this::EntityToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    FilmServiceImpl(){
        modelMapper=new ModelMapper();
    }

    public FilmDto EntityToDto(Film film){
        return modelMapper.map(film, FilmDto.class);
    }

    public Film DtoToEntity(FilmDto filmDto){
        return modelMapper.map(filmDto, Film.class);
    }

    @Override
    public FilmDto createNewFilm(FilmReceiveDto filmReceiveDto) {
        return null;
    }

    @Override
    public ContentRatingDto getContentRatingByFilmId(Long filmId) {
        return null;
    }

    @Override
    public List<CategoryDto> getAllCategoriesByFilmId(Long filmId) {
        return null;
    }

    @Override
    public List<ActorDto> getAllActorsByFilmId(Long filmId) {
        return null;
    }

    @Override
    public List<AwardDto> getAllAwardsByFilmId(Long filmId) {
        return null;
    }

    @Override
    public List<ExternalMovie> searchFilmInExternalApi(String title) {
        return null;
    }

    @Override
    public List<FilmDto> searchExistingFilm(String title) {
        List<Film> filmsSearched = filmRepository.findByTitleContainingIgnoreCase(title);

        // Si no se encuentran coincidencias, devolver una lista vacía
        if (filmsSearched.isEmpty()) {
            return Collections.emptyList();
        }

        // Mapear la lista de películas a una lista de FilmDto
        List<FilmDto> filmDtos = filmsSearched.stream()
                .map(film -> modelMapper.map(film, FilmDto.class))
                .collect(Collectors.toList());

        return filmDtos;
    }

    @Override
    public List<FilmDto> getFilmsByCategory(String category) {

        if(!categoryRepository.existsByName(category)){
            throw new RuntimeException("No existe la categoría con el nombre: " + category);
        }

        List<Film> films = filmRepository.findAll();
        //buscamos las categorías de cada película y filtramos las que contengan la categoría, como la categoría es un objeto
        // del tipo Category, tenemos que hacer un map para obtener el nombre de la categoría y compararlo con el nombre de la categoría
        // que nos pasan por parámetro
        return films.stream()
                .filter(film -> film.getCategories().stream()
                        .map(Category::getName)
                        .anyMatch(categoryName -> categoryName.equals(category)))
                .map(this::EntityToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public FilmDto addCategoriesToFilmByCategoriesIds(Long filmId, List<Long> categoriesIds) {
        return null;
    }

    @Override
    public FilmDto addActorsToFilmByActorsIds(Long filmId, List<Long> actorsIds) {
        return null;
    }

    @Override
    public FilmDto addAwardsToFilmByAwardsIds(Long filmId, List<Long> awardsIds) {
        return null;
    }

    @Override
    public FilmDto addCineclubToFilmByCineclubId(Long filmId, Long cineclubId) {
        return null;
    }

    @Override
    public FilmDto getFilmByTitle(String title) {
        return null;
    }

    @Override
    public FilmDto getFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("No se encontró la película con el ID: " + filmId));

        return modelMapper.map(film, FilmDto.class);
    }
}
