package com.tucine.cineclubadministration.Cineclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tucine.cineclubadministration.Film.model.Film;
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
@Table(name = "Cineclub")
public class Cineclub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "address", nullable = false, length = 200)
    private String address;
    @Column(name = "phone", nullable = false, length = 9)
    private String phone;
    @Column(name = "open_in_hours", nullable = false, length = 100)
    private Date openInHours;
    @Column(name = "social_reason", nullable = false, length = 255)
    private String socialReason;
    @Column(name = "RUC", nullable = false, length = 11)
    private String RUC;
    @Column(name = "logo_src", nullable = false, length = 2000)
    private String logoSrc;
    @Column(name = "banner_src", nullable = false, length = 2000)
    private String bannerSrc;
    @Column(name = "state", nullable = false, length = 20)
    private String state;
    @Column(name = "description", nullable = false, length = 2000)
    private String description;
    @Column(name = "capacity", nullable = false, length = 11)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "cineclub_type_id", nullable = false,foreignKey = @ForeignKey(name = "FK_cineclub_cineclub_type"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CineclubType cineclubType;

    @JsonIgnore
    @ManyToMany
    List<Film> films;

}
