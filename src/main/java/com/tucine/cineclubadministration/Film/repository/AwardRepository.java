package com.tucine.cineclubadministration.Film.repository;

import com.tucine.cineclubadministration.Film.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award,Long> {
    boolean existsByName(String name);
}
