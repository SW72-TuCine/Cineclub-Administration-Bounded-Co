package com.tucine.cineclubadministration.Film.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tucine.cineclubadministration.Film.helpers.TheMovieDatabaseHelper;
import com.tucine.cineclubadministration.Film.model.ExternalMovie;
import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Cineclub.repository.CineclubRepository;
import com.tucine.cineclubadministration.Film.dto.normal.*;
import com.tucine.cineclubadministration.Film.dto.receive.FilmReceiveDto;
import com.tucine.cineclubadministration.Film.model.*;
import com.tucine.cineclubadministration.Film.repository.*;
import com.tucine.cineclubadministration.Film.service.interf.FilmService;
import com.tucine.cineclubadministration.shared.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private ContentRatingRepository contentRatingRepository;
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

    private <T> List<T> getEntitiesByIds(List<Long> ids, JpaRepository<T, Long> repository) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return repository.findAllById(ids);
    }

    @Override
    public ContentRatingDto getContentRatingByFilmId(Long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el id: " + filmId));

        return modelMapper.map(film.getContentRating(), ContentRatingDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategoriesByFilmId(Long filmId) {

        Film film =filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el id: " + filmId));

        return film.getCategories().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<ActorDto> getAllActorsByFilmId(Long filmId) {

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el id: " + filmId));

        return film.getActors().stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<AwardDto> getAllAwardsByFilmId(Long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el id: " + filmId));

        return film.getAwards().stream()
                .map(award -> modelMapper.map(award, AwardDto.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<ExternalMovie> searchFilmInExternalApi(String title) {

        String jsonResponse = TheMovieDatabaseHelper.getStringResponseForSearchFilmAPI(title);

        //Crear una lista para almacenar los objetos ExternalMovie
        List<ExternalMovie> ListExternalMovie = new java.util.ArrayList<>(Collections.emptyList());

        try{
            //Configurar el ObjectMapper de Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            //Parsear el JSON a un nodo JSON
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            //Obtener la matriz "results" del nodo JSON
            ArrayNode resultsArrayNode = (ArrayNode) jsonNode.get("results");

            for(JsonNode movieNode: resultsArrayNode){
                ExternalMovie externalMovie = new ExternalMovie();

                //obtener el id
                externalMovie.setId(movieNode.get("id").asInt());
                externalMovie.setTitle(movieNode.get("original_title").asText());
                externalMovie.setOverview(movieNode.get("overview").asText());
                //ruta para obtener la imagen
                externalMovie.setPosterPath("https://image.tmdb.org/t/p/original/"+movieNode.get("poster_path").asText());
                //obtener la fecha de lanzamiento
                externalMovie.setReleaseDate(movieNode.get("release_date").asText());
                //obtener la categoria
                List<Integer> listCategoryIdExternalAPI = new java.util.ArrayList<>(Collections.emptyList());
                for(JsonNode genre: movieNode.get("genre_ids")){
                    listCategoryIdExternalAPI.add(genre.asInt());
                }
                List<String> listCategories = TheMovieDatabaseHelper.getAllCategoriesFromExternalMovieAPI(listCategoryIdExternalAPI);
                externalMovie.setGenres(listCategories);

                //Se agrega el objeto a la lista
                ListExternalMovie.add(externalMovie);
            }

            return ListExternalMovie;


        } catch (Exception e) {
            e.printStackTrace();
        }


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
    public FilmReceiveDto getInformationMovieFromExternalApi(String filmExternalId) {
        return TheMovieDatabaseHelper.getInformationAboutMovieFromExternalAPI(filmExternalId);
    }

    @Override
    public FilmDto saveInformationAboutFilmAndAsociateWithCineclub(Long cineclubId, String movieExternalId) {

        // 1. Verificar/Obtener el cineclub de la base de datos
        Cineclub cineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new ValidationException("No se encontró el cineclub con el ID: " + cineclubId));

        // 2. Extraer toda la información de la película de la API externa
        FilmReceiveDto filmReceiveDto = TheMovieDatabaseHelper.getInformationAboutMovieFromExternalAPI(movieExternalId);

        // 3. Verificar si el ContentRating ya existe en la base de datos
        ContentRating contentRating = contentRatingRepository.findByName(filmReceiveDto.getContentRating().getName());

        // 4. Verificar si los actores ya existen en la base de datos y crearlos si no existen
        List<Actor> actors = getOrCreateActors(filmReceiveDto.getActors());

        // 5. Verificar si las categorías ya existen en la base de datos y crearlas si no existen
        List<Category> categories = getOrCreateCategories(filmReceiveDto.getCategories());

        //filmReceiveDto
        filmReceiveDto.setContentRating(contentRating);
        filmReceiveDto.setActors(actors);
        filmReceiveDto.setCategories(categories);

        // 6. Instanciar una pelicula
        Film film= new Film();

        //Verificar si la película ya existe en la base de datos
        if (filmRepository.existsByTitleAndYearAndDuration(filmReceiveDto.getTitle(), filmReceiveDto.getYear(), filmReceiveDto.getDuration())) {
            // Si la película existe, obtenerla de la base de datos
            film = filmRepository.findByTitleAndYearAndDuration(filmReceiveDto.getTitle(), filmReceiveDto.getYear(), filmReceiveDto.getDuration());
        } else {
            // Si la película no existe, crearla y asociarla con el ContentRating y las categorías
            FilmDto filmDto = createNewFilm(filmReceiveDto);
            film = filmRepository.findByTitleAndYearAndDuration(filmReceiveDto.getTitle(), filmReceiveDto.getYear(), filmReceiveDto.getDuration());
        }

        if(film.getCineclubs()==null){
            film.setCineclubs(new ArrayList<>());
        }
        if(cineclub.getFilms()==null){
            cineclub.setFilms(new ArrayList<>());
        }
        // 9. Asociar la película con el cineclub
        if (!film.getCineclubs().contains(cineclub)) {
            film.getCineclubs().add(cineclub);
            cineclub.getFilms().add(film);
        }

        // 10. Guardar la película
        film = filmRepository.save(film);

        // 11. Mapear y devolver la película guardada como DTO
        return modelMapper.map(film, FilmDto.class);
    }


    private List<Actor> getOrCreateActors(List<Actor> actorsToCreate) {
        List<Actor> actors = new ArrayList<>();
        for (Actor actualActor : actorsToCreate) {
            // Verificar si el actor ya existe en la base de datos
            Actor existingActor = actorRepository.findByFirstName(actualActor.getFirstName());
            if (existingActor != null) {
                actors.add(existingActor);
            } else {
                // Si el actor no existe, crearlo y guardarlo en la base de datos
                Actor newActor = new Actor();
                newActor.setFirstName(actualActor.getFirstName());
                if (actualActor.getLastName() == null) {
                    newActor.setLastName("");
                }else {
                    newActor.setLastName(actualActor.getLastName());
                }
                if(actualActor.getBirthdate() == null){
                    newActor.setBirthdate("");
                }else
                {
                    newActor.setBirthdate(actualActor.getBirthdate());
                }
                // Verificar si la biografía está presente y establecer un valor predeterminado si no lo está
                if (actualActor.getBiography() != null) {
                    newActor.setBiography(actualActor.getBiography());
                } else {
                    newActor.setBiography("Biografía no disponible");
                }
                if(actualActor.getPhotoSrc() == null){
                    newActor.setPhotoSrc("");
                }else
                {
                    newActor.setPhotoSrc(actualActor.getPhotoSrc());
                }

                actors.add(actorRepository.save(newActor));
            }
        }
        return actors;
    }


    private List<Category> getOrCreateCategories(List<Category> category) {
        List<Category> categories = new ArrayList<>();

        for (Category actualCategory : category) {
            // Verificar si la categoría ya existe en la base de datos
            Category existingCategory = categoryRepository.findByName(actualCategory.getName());
            if (existingCategory != null) {
                categories.add(existingCategory);
            } else {
                // Si la categoría no existe, crearla y guardarla en la base de datos
                Category newCategory = new Category();
                newCategory.setName(actualCategory.getName());
                categories.add(categoryRepository.save(newCategory));
            }
        }
        return categories;
    }
    @Override
    public List<FilmDto> getFilmsByCategory(String category) {

        if(!categoryRepository.existsByName(category)){
            throw new ValidationException("No existe la categoría con el nombre: " + category);
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
        // Verificar que la lista de categorías no esté vacía
        if (categoriesIds.isEmpty()) {
            throw new ValidationException("La lista de categorías no puede estar vacía");
        }
        // Obtener la película por su ID (si no se encuentra, lanzará una excepción)
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el ID: " + filmId));

        // Verificar la existencia de categorías y evitar duplicados
        for (Long categoryId : categoriesIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ValidationException("No se encontró la categoría con el ID: " + categoryId));

            if (!film.getCategories().contains(category)) {
                film.getCategories().add(category);
            }
        }
        // Guardar la película actualizada
        film = filmRepository.save(film);

        // Mapear y devolver la película actualizada
        return modelMapper.map(film, FilmDto.class);
    }

    @Override
    public FilmDto addActorsToFilmByActorsIds(Long filmId, List<Long> actorsIds) {


        if(actorsIds.isEmpty()){
            throw new ValidationException("La lista de actores no puede estar vacía");
        }

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el ID: " + filmId));

        for(Long actorId: actorsIds){
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new ValidationException("No se encontró el actor con el ID: " + actorId));

            if(!film.getActors().contains(actor)){
                film.getActors().add(actor);
            }
        }

        film = filmRepository.save(film);

        return modelMapper.map(film, FilmDto.class);

    }

    @Override
    public FilmDto addAwardsToFilmByAwardsIds(Long filmId, List<Long> awardsIds) {
        if(awardsIds.isEmpty()){
            throw new ValidationException("La lista de premios no puede estar vacía");
        }

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el ID: " + filmId));

        for(Long awardId: awardsIds){
            Award award = awardRepository.findById(awardId)
                    .orElseThrow(() -> new ValidationException("No se encontró el premio con el ID: " + awardId));

            if(!film.getAwards().contains(award)){
                film.getAwards().add(award);
            }
        }

        film = filmRepository.save(film);

        return modelMapper.map(film, FilmDto.class);
    }

    @Override
    public FilmDto addCineclubToFilmByCineclubId(Long filmId, Long cineclubId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el ID: " + filmId));

        Cineclub cineclub = cineclubRepository.findById(cineclubId)
                .orElseThrow(() -> new ValidationException("No se encontró el cineclub con el ID: " + cineclubId));

        if(!film.getCineclubs().contains(cineclub)){
            film.getCineclubs().add(cineclub);
        }

        if(!cineclub.getFilms().contains(film)){
            cineclub.getFilms().add(film);
        }

        film = filmRepository.save(film);

        cineclubRepository.save(cineclub);

        return modelMapper.map(film, FilmDto.class);
    }

    @Override
    public FilmDto getFilmByTitle(String title) {

        //TODO: Verificar si esto está correcto

        Film film = filmRepository.findByTitle(title);
        if(film == null){
            throw new ValidationException("No se encontró la película con el título: " + title);
        }

        return modelMapper.map(film, FilmDto.class);
    }

    @Override
    public FilmDto getFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ValidationException("No se encontró la película con el ID: " + filmId));

        return modelMapper.map(film, FilmDto.class);
    }

    @Override
    public boolean checkIfFilmExist(Long FilmId) {
        return filmRepository.existsById(FilmId);
    }

    private void validateFilm(FilmReceiveDto filmReceiveDto) {
        if(filmReceiveDto.getTitle() == null || filmReceiveDto.getTitle().isEmpty()){
            throw new ValidationException("El título de la película no puede estar vacío");
        }
/*        if(filmReceiveDto.getDuration() <= 0){
            throw new ValidationException("La duración de la película no puede ser menor o igual a 0");
        }*/
/*        if(filmReceiveDto.getSynopsis() == null || filmReceiveDto.getSynopsis().isEmpty()){
            throw new ValidationException("La sinopsis de la película no puede estar vacía");
        }*/
/*        if(filmReceiveDto.getPosterSrc() == null || filmReceiveDto.getPosterSrc().isEmpty()){
            throw new ValidationException("La ruta del poster de la película no puede estar vacía");
        }*/
/*        if(filmReceiveDto.getTrailerSrc() == null || filmReceiveDto.getTrailerSrc().isEmpty()){
            throw new RuntimeException("La ruta del trailer de la película no puede estar vacía");
        }*/
/*        if(filmReceiveDto.getActors() == null || filmReceiveDto.getActors().isEmpty()){
            throw new RuntimeException("La lista de actores de la película no puede estar vacía");
        }*/
        if(filmReceiveDto.getCategories() == null || filmReceiveDto.getCategories().isEmpty()){
            throw new ValidationException("La lista de categorías de la película no puede estar vacía");
        }
        if(filmReceiveDto.getContentRating() == null){
            throw new ValidationException("La clasificación de contenido de la película no puede estar vacía");
        }
    }
}
