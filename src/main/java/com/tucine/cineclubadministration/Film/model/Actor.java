package com.tucine.cineclubadministration.Film.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name = "last_name", nullable = true,length = 100)
    private String lastName;
    @Column(name = "birthdate", nullable = true,length = 100)
    private String birthdate;
    @Column(name = "biography", nullable = true, length = 1000)
    private String biography;
    @Column(name = "photo_src", nullable = true,length = 1000)
    private String photoSrc;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private List<Film> films;

}
