package com.tucine.cineclubadministration.Film.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.tucine.cineclubadministration.Film.dto.normal.ActorDto;
import com.tucine.cineclubadministration.Film.dto.receive.ActorReceiveDto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TheMovieDatabaseHelper {

    static String API_KEY="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMjhjMjQ4YWEwNTMwOGIzMDUyYWZiNWQ4MzU1NmY2ZSIsInN1YiI6IjY1MGI5NTdmMmM2YjdiMDBmZTQ1YjY5YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zQL9P8NaXE9qwDIWk3Jzgc0m0R7QqjFdBiXQl0k3AXs";
    static String DEFAULT_LANGUAGE="es-PE";

    public static String convertRatingUStoOurRatingFormat(String ratingUS){
        return switch (ratingUS) {
            case "G", "PG" -> "APT";
            case "PG-13", "R" -> "M14";
            case "NC-17" -> "M18";
            default -> "NN";
        };
    }

    public static String getResponseBodyFromRequest(Request request){
        OkHttpClient client = new OkHttpClient();
        try{
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                return response.body().string();
            }else{
                return null;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Request requestBuilder(String URL,String API_KEY){
        return new Request.Builder()
                .url(URL)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();
    }
    public static String DateTimeFormatterGetYears(String date){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate=LocalDate.parse(date,formatter);

        return localDate.getYear()+"";
    }
    public static String getMovieTrailerSrcVideo(String idMovieFromTheMovieDatabase){

        String youtube_link="https://www.youtube.com/watch?v=";

        String URL="https://api.themoviedb.org/3/movie/"+idMovieFromTheMovieDatabase+"/videos?language="+DEFAULT_LANGUAGE;

        Request request=requestBuilder(URL,API_KEY);

        String responseBody=getResponseBodyFromRequest(request);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            //Obtener el primer elemento del arreglo results:
            JsonNode firstResult = jsonNode.get("results").get(0);
            String video_youtube_key=firstResult.get("key").asText();
            return youtube_link+video_youtube_key;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Integer getDurationExternalMovie(String idMovieFromTheMovieDatabase){

        //https://api.themoviedb.org/3/movie/39108?language=en-US
        String URL="https://api.themoviedb.org/3/movie/"+idMovieFromTheMovieDatabase+"?language="+DEFAULT_LANGUAGE;

        Request request=requestBuilder(URL,API_KEY);

        String responseBody=getResponseBodyFromRequest(request);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            //Obtener el tiempo de duración de la película:
            return jsonNode.get("runtime").asInt();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String getContentRatingNameFromExternalMovie(String idMovieFromTheMovieDatabase){
        String URL="https://api.themoviedb.org/3/movie/"+idMovieFromTheMovieDatabase+"/release_dates";

        Request request=requestBuilder(URL,API_KEY);

        String responseBody=getResponseBodyFromRequest(request);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            //Obtener la lista de resultados:
            JsonNode results = jsonNode.get("results");
            for(JsonNode result: results){
                String iso3166 = result.get("iso_3166_1").asText();

                if("US".equals(iso3166)){
                    JsonNode realease_dates = result.get("release_dates");
                    for(JsonNode release_date: realease_dates){
                        String certification = release_date.get("certification").asText();
                        return convertRatingUStoOurRatingFormat(certification);
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Integer> getFirstFiveActorIdsFromExternalMovie(String idMovieFromTheMovieDatabase) {
        String URL_CAST = "https://api.themoviedb.org/3/movie/" + idMovieFromTheMovieDatabase + "/credits?language=" + DEFAULT_LANGUAGE;

        Request request = requestBuilder(URL_CAST, API_KEY);

        String responseBody = getResponseBodyFromRequest(request);

        if (responseBody != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // Obtén la lista de actores del JSON
                JsonNode castNode = jsonNode.get("cast");

                // Crear una lista para almacenar los IDs de los primeros 5 actores
                List<Integer> firstFiveActorIds = new ArrayList<>();
                // Agregar los IDs de los primeros 5 actores a la lista
                for (JsonNode actorNode : castNode) {
                    int actorId = actorNode.get("id").asInt();
                    firstFiveActorIds.add(actorId);
                    // Si ya se han agregado 5 actores, salir del bucle
                    if (firstFiveActorIds.size() >= 5) {
                        break;
                    }
                }
                return firstFiveActorIds;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    public static List<ActorReceiveDto> getActorsFromExternalMovie(String idMovieFromTheMovieDatabase) {

        List<Integer> actorIds = getFirstFiveActorIdsFromExternalMovie(idMovieFromTheMovieDatabase);

        List<ActorReceiveDto> actors = new ArrayList<>();

        for (Integer actorId : actorIds) {
            String actorUrl = "https://api.themoviedb.org/3/person/" + actorId + "?language=" + DEFAULT_LANGUAGE;
            Request actorRequest = requestBuilder(actorUrl, API_KEY);
            String actorResponseBody = getResponseBodyFromRequest(actorRequest);

            if (actorResponseBody != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode actorJsonNode = objectMapper.readTree(actorResponseBody);

                    // Mapear la respuesta JSON a ActorReceiveDto
                    ActorReceiveDto actorDto = new ActorReceiveDto();
                    actorDto.setFirstName(actorJsonNode.get("name").asText());
                    actorDto.setLastName("");  // Si no hay apellido en la respuesta, puedes dejarlo como una cadena vacía
                    actorDto.setBirthdate(actorJsonNode.get("birthday").asText());
                    actorDto.setBiography(actorJsonNode.get("biography").asText());
                    actorDto.setPhotoSrc("https://image.tmdb.org/t/p/original" + actorJsonNode.get("profile_path").asText());

                    actors.add(actorDto);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        return actors;
    }

}
