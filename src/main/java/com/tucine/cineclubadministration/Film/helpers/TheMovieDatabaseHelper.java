package com.tucine.cineclubadministration.Film.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        OkHttpClient client = new OkHttpClient();

        String URL="https://api.themoviedb.org/3/movie/"+idMovieFromTheMovieDatabase+"/videos?language="+DEFAULT_LANGUAGE;

        Request request=requestBuilder(URL,API_KEY);

        try{
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                //Obtener el primer elemento del arreglo results:
                JsonNode firstResult = jsonNode.get("results").get(0);
                String video_youtube_key=firstResult.get("key").asText();

                return youtube_link+video_youtube_key;

            }else{
                return "Respuesta no es exitosa";
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return "Respuesta no es exitosa";
    }
    public static Integer getDurationExternalMovie(String idMovieFromTheMovieDatabase){

        //https://api.themoviedb.org/3/movie/39108?language=en-US
        String URL="https://api.themoviedb.org/3/movie/"+idMovieFromTheMovieDatabase+"?language="+DEFAULT_LANGUAGE;

        OkHttpClient client = new OkHttpClient();

        Request request=requestBuilder(URL,API_KEY);

        try{
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                //Obtener el tiempo de duración de la película:
                return jsonNode.get("runtime").asInt();

            }else{
                return null;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
    public static String getContentRatingExternalMovie(String idMovieFromTheMovieDatabase){
        String URL="https://api.themoviedb.org/3/movie/"+idMovieFromTheMovieDatabase+"/release_dates";

        OkHttpClient client = new OkHttpClient();

        Request request=requestBuilder(URL,API_KEY);

        try{
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBody = response.body().string();
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
            }else{
                return null;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
