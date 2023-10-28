package com.tucine.cineclubadministration.Film.dto.receive;

import com.tucine.cineclubadministration.Film.model.Film;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActorReceiveDto {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String biography;
    private String photoSrc;
}
