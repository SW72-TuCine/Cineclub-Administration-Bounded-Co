package com.tucine.cineclubadministration.Cineclub.repository;

import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CineclubRepository extends JpaRepository<Cineclub, Long> {
    boolean existsByName(String name);

    Cineclub findByName(String name);
}
