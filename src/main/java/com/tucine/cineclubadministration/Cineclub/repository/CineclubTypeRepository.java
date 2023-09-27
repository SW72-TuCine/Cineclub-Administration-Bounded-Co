package com.tucine.cineclubadministration.Cineclub.repository;

import com.tucine.cineclubadministration.Cineclub.model.CineclubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CineclubTypeRepository extends JpaRepository<CineclubType, Long> {
    boolean existsByName(String name);
}
