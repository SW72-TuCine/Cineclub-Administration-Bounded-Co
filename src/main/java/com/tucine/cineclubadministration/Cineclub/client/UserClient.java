package com.tucine.cineclubadministration.Cineclub.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@FeignClient(name = "account-managment-service")
public interface UserClient {

    @RequestMapping("/api/TuCine/v1/account_management/users/verify/{userId}")
    boolean checkIfUserExist(@PathVariable("userId") Long userId) throws RuntimeException;
}

