package com.leon.springbootleon.controller;

import com.leon.springbootleon.model.dto.request.AppUserDto;
import com.leon.springbootleon.model.dto.response.CustomApiResponse;
import com.leon.springbootleon.service.AppUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/user")
@Tag(name = "User API")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/creat")
    public ResponseEntity<?> createUser(@RequestBody AppUserDto appUserDto){
        appUserService.createUser(appUserDto);
        return ResponseEntity.ok(new CustomApiResponse());
    }
}
