package com.tucine.cineclubadministration.Film.repository;

import com.tucine.cineclubadministration.Film.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    boolean existsByTitle(String title);

    /*    List<Film> findByTitleIgnoreCaseContaining(String title);*/
    List<Film> findByTitleContainingIgnoreCase(String title);

    boolean existsByTitleAndYearAndDuration(String title, String year, int duration);

    Film findByTitleAndYearAndDuration(String title, String year, int duration);
    Film findByTitle(String title);

    Film findById(long id);

}
