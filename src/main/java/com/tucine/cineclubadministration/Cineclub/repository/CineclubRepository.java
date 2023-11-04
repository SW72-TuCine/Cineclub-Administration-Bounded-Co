package com.tucine.cineclubadministration.Cineclub.repository;

import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Cineclub.model.CineclubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CineclubRepository extends JpaRepository<Cineclub, Long> {
    boolean existsByName(String name);

    List<Cineclub> getAllByCineclubTypeNameContains(String categorieName);

    List<Cineclub> getAllByNameContains(String cineclubName);
    Cineclub findByName(String name);

    boolean existsById(Long id);
}
