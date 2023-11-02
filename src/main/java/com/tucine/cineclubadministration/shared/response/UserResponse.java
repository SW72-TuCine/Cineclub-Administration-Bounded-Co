package com.tucine.cineclubadministration.shared.response;

import lombok.Data;

@Data
public class UserResponse {
    Long id;
    String firstName;
    TypeUser typeUser;
}
