package com.tucine.cineclubadministration.Film.repository;

import com.tucine.cineclubadministration.Film.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
}
