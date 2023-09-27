package com.tucine.cineclubadministration.Film.service.interf;

import com.tucine.cineclubadministration.Film.dto.normal.CategoryDto;
import com.tucine.cineclubadministration.Film.dto.normal.ContentRatingDto;
import com.tucine.cineclubadministration.Film.dto.receive.CategoryReceiveDto;
import com.tucine.cineclubadministration.Film.dto.receive.ContentRatingReceiveDto;

import java.util.List;

public interface ContentRatingService {

    List<ContentRatingDto> getAllContentRatings();
    ContentRatingDto createContentRating(ContentRatingReceiveDto contentRatingReceiveDto);
    ContentRatingDto modifyContentRating(Long contentRatingId, ContentRatingReceiveDto contentRatingReceiveDto);
    public void deleteContentRating(Long contentRatingId);
}
