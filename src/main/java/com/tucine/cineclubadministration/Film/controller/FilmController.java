package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.*;
import com.tucine.cineclubadministration.Film.dto.receive.FilmReceiveDto;
import com.tucine.cineclubadministration.Film.model.ExternalMovie;
import com.tucine.cineclubadministration.Film.service.interf.FilmService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/TuCine/v1/cineclub_administration")
public class FilmController {
    @Autowired
    private FilmService filmService;

    public static final String FILM_SERVICE="filmService";


    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films
    @Transactional(readOnly = true)
    @GetMapping("/films")
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        List<FilmDto> listFilmDto = filmService.getAllFilms();
        return ResponseEntity.ok(listFilmDto);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/{filmId}
    @Transactional
    @GetMapping("/films/getById/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable("id") Long filmId) {
        FilmDto filmDto = filmService.getFilmById(filmId);
        return ResponseEntity.ok(filmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/{category}
    @Transactional(readOnly = true)
    @GetMapping("/films/{category}")
    public ResponseEntity<List<FilmDto>> getFilmsByCategory(@PathVariable("category") String category) {
        List<FilmDto> listFilmDto = filmService.getFilmsByCategory(category);
        return ResponseEntity.ok(listFilmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/search?title=blue
    @Transactional(readOnly = true)
    @GetMapping("/films/search")
    public ResponseEntity<List<FilmDto>> searchExistingFilm(@RequestParam("title") String title) {
        List<FilmDto> films = filmService.searchExistingFilm(title);
        return ResponseEntity.ok(films);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films
    @Transactional
    @PostMapping("/films")
    public ResponseEntity<FilmDto> createFilm(@RequestBody FilmReceiveDto filmReceiveDto) {
        FilmDto filmDtoCreated = filmService.createNewFilm(filmReceiveDto);
        return ResponseEntity.ok(filmDtoCreated);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/contentRating
    @Transactional(readOnly = true)
    @GetMapping("/films/{id}/contentRating")
    public ResponseEntity<ContentRatingDto> getContentRating(@PathVariable("id") Long id) {
        ContentRatingDto contentRating = filmService.getContentRatingByFilmId(id);
        return ResponseEntity.ok(contentRating);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/categories
    @Transactional(readOnly = true)
    @GetMapping("/films/{id}/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(@PathVariable("id") Long id) {
        List<CategoryDto> categories = filmService.getAllCategoriesByFilmId(id);
        return ResponseEntity.ok(categories);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/actors
    @Transactional(readOnly = true)
    @GetMapping("/films/{id}/actors")
    public ResponseEntity<List<ActorDto>> getActors(@PathVariable("id") Long id) {
        List<ActorDto> actors = filmService.getAllActorsByFilmId(id);
        return ResponseEntity.ok(actors);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/awards
    @Transactional(readOnly = true)
    @GetMapping("/films/{id}/awards")
    public ResponseEntity<List<AwardDto>> getAwards(@PathVariable("id") Long id) {
        List<AwardDto> awards = filmService.getAllAwardsByFilmId(id);
        return ResponseEntity.ok(awards);
    }

    // URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/externalApi/{filmExternalId}
    @Transactional(readOnly = true)
    @GetMapping("/films/externalApi/{filmExternalId}")
    public ResponseEntity<FilmReceiveDto> getInformationMovieFromExternalApi(@PathVariable("filmExternalId") String filmExternalId) {
        FilmReceiveDto filmReceiveDto = filmService.getInformationMovieFromExternalApi(filmExternalId);
        return ResponseEntity.ok(filmReceiveDto);
    }

    @Transactional
    @PostMapping("/films/saveInformationAboutFilmAndAsociateWithCineclub/{cineclubId}/{movieExternalId}")
    public ResponseEntity<FilmDto> saveInformationAboutFilmAndAsociateWithCineclub(@PathVariable ("cineclubId") Long cineclubId, @PathVariable ("movieExternalId") String movieExternalId) {
        FilmDto filmDto = filmService.saveInformationAboutFilmAndAsociateWithCineclub(cineclubId, movieExternalId);
        return ResponseEntity.ok(filmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/categories?categoriesIds=1,2,3
    @Transactional
    @PostMapping("/films/{id}/categories")
    public ResponseEntity<FilmDto> addCategoriesToFilm(@PathVariable("id") Long filmId, @RequestParam("categoriesIds") List<Long> categoriesIds) {
        FilmDto filmDto = filmService.addCategoriesToFilmByCategoriesIds(filmId, categoriesIds);
        return ResponseEntity.ok(filmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/actors?actorsIds=1,2,3
    @Transactional
    @PostMapping("/films/{id}/actors")
    public ResponseEntity<FilmDto> addActorsToFilm(@PathVariable("id") Long filmId, @RequestParam("actorsIds") List<Long> actorsIds) {
        FilmDto filmDto = filmService.addActorsToFilmByActorsIds(filmId, actorsIds);
        return ResponseEntity.ok(filmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/awards?awardsIds=1,2,3
    @Transactional
    @PostMapping("/films/{id}/awards")
    public ResponseEntity<FilmDto> addAwardsToFilm(@PathVariable("id") Long filmId, @RequestParam("awardsIds") List<Long> awardsIds) {
        FilmDto filmDto = filmService.addAwardsToFilmByAwardsIds(filmId, awardsIds);
        return ResponseEntity.ok(filmDto);
    }

    //URL: http://localhost:8080/api/TuCine/v1/cineclub_administration/films/1/cineclub?cineclubId=1
    @Transactional
    @PostMapping("/films/{id}/cineclub")
    public ResponseEntity<FilmDto> addCineclubToFilm(@PathVariable("id") Long filmId, @RequestParam("cineclubId") Long cineclubId) {
        FilmDto filmDto = filmService.addCineclubToFilmByCineclubId(filmId, cineclubId);
        return ResponseEntity.ok(filmDto);
    }

    //URL : http://localhost:8080/api/TuCine/v1/cineclub_administration/films/search/externalApi?title=blue
    @Transactional(readOnly = true)
    @GetMapping("/films/search/externalApi")

    //@CircuitBreaker(name = "externalApi", fallbackMethod = "searchFilmInExternalAPIFallback")
    public ResponseEntity<List<ExternalMovie>> searchFilmInExternalAPI(@RequestParam("title") String title) {
        try {
            // Intenta llamar al servicio externo
            List<ExternalMovie> listExternalMovie = filmService.searchFilmInExternalApi(title);

            // Si tienes éxito, devuelve la respuesta normalmente
            return ResponseEntity.ok(listExternalMovie);
        } catch (Exception e) {
            // Si hay un fallo, el circuit breaker activará el fallback
            // El mensaje de fallback se incluirá en la respuesta HTTP que envías
            String fallbackMessage = String.valueOf(searchFilmInExternalAPIFallback(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new ExternalMovie(0, fallbackMessage, fallbackMessage, "", "", Collections.emptyList())));
        }
    }

    public ResponseEntity<String> searchFilmInExternalAPIFallback(Throwable throwable) {
        // Maneja la excepción y devuelve un mensaje de error en una respuesta HTTP
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error, no se pudo conectar con la API externa");
    }


    @RequestMapping("/films/verify/{filmId}")
    boolean checkIfFilmExist(@PathVariable Long filmId){
        return filmService.checkIfFilmExist(filmId);
    }

}
