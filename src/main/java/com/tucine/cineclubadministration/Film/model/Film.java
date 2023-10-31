package com.tucine.cineclubadministration.Film.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tucine.cineclubadministration.Cineclub.model.Cineclub;
import com.tucine.cineclubadministration.Film.model.Actor;
import com.tucine.cineclubadministration.Film.model.Award;
import com.tucine.cineclubadministration.Film.model.Category;
import com.tucine.cineclubadministration.Film.model.ContentRating;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    @Column(name = "year", nullable = false, length = 4)
    private String year;
    @Column(name="synopsis", nullable = false, length = 2000)
    private String synopsis;
    @Column(name="poster_src", nullable = false, length = 2000)
    private String posterSrc;
    @Column(name="trailer_src", nullable = false, length = 2000)
    private String trailerSrc;
    @Nullable
    @Column(name="duration", nullable = false, length = 4)
    private int duration;

    @ManyToOne
    @JoinColumn(name = "content_rating_id", nullable = false,foreignKey = @ForeignKey(name = "FK_Film_ContentRating"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ContentRating contentRating;

    @JsonIgnore
    @ManyToMany
    private List<Actor> actors;

    @JsonIgnore
    @ManyToMany
    private List<Award> awards;

    @JsonIgnore
    @ManyToMany
    private List<Category> categories;

    @JsonIgnore
    @ElementCollection
    private List<Long>actorsIds=new ArrayList<>();

    @JsonIgnore
    @ElementCollection
    private List<Long> awardIds = new ArrayList<>();

    @JsonIgnore
    @ElementCollection
    private List<Long> categoryIds = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "films")
    private List<Cineclub> cineclubs;

}
