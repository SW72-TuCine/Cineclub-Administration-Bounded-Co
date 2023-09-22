package com.tucine.cineclubadministration.Film.repository;

import com.tucine.cineclubadministration.Film.model.ContentRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRatingRepository extends JpaRepository<ContentRating,Long>
{
    boolean existsByName(String name);
}
