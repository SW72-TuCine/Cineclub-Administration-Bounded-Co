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
    @Nullable
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Nullable
    @Column(name = "birthdate", nullable = false, length = 100)
    private String birthdate;
    @Nullable
    @Column(name = "biography", nullable = false, length = 1000)
    private String biography;
    @Nullable
    @Column(name = "photo_src", nullable = false, length = 1000)
    private String photoSrc;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private List<Film> films;

}
