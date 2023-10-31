package com.tucine.cineclubadministration.Film.repository;

import com.tucine.cineclubadministration.Film.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Long> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Actor findByFirstName(String firstName);
}
