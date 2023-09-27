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

        validateFilm(filmReceiveDto);

        FilmDto filmDto = modelMapper.map(filmReceiveDto, FilmDto.class);

        Film film = DtoToEntity(filmDto);

        return EntityToDto(filmRepository.save(film));
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


    private void validateFilm(FilmReceiveDto filmReceiveDto) {
        if(filmReceiveDto.getTitle() == null || filmReceiveDto.getTitle().isEmpty()){
            throw new RuntimeException("El título de la película no puede estar vacío");
        }
        if(filmReceiveDto.getDuration() <= 0){
            throw new RuntimeException("La duración de la película no puede ser menor o igual a 0");
        }
        if(filmReceiveDto.getSynopsis() == null || filmReceiveDto.getSynopsis().isEmpty()){
            throw new RuntimeException("La sinopsis de la película no puede estar vacía");
        }
        if(filmReceiveDto.getPosterSrc() == null || filmReceiveDto.getPosterSrc().isEmpty()){
            throw new RuntimeException("La ruta del poster de la película no puede estar vacía");
        }
/*        if(filmReceiveDto.getTrailerSrc() == null || filmReceiveDto.getTrailerSrc().isEmpty()){
            throw new RuntimeException("La ruta del trailer de la película no puede estar vacía");
        }*/
/*        if(filmReceiveDto.getActors() == null || filmReceiveDto.getActors().isEmpty()){
            throw new RuntimeException("La lista de actores de la película no puede estar vacía");
        }*/
/*        if(filmReceiveDto.getCategories() == null || filmReceiveDto.getCategories().isEmpty()){
            throw new RuntimeException("La lista de categorías de la película no puede estar vacía");
        }*/
        if(filmReceiveDto.getContentRating() == null){
            throw new RuntimeException("La clasificación de contenido de la película no puede estar vacía");
        }
    }
}
