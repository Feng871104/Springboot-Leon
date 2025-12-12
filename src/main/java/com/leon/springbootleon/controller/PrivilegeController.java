package com.leon.springbootleon.controller;

import com.leon.springbootleon.repository.AppUserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/privilege", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Privilege API")
public class PrivilegeController {

    private final AppUserRepository appUserRepository;

    public PrivilegeController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPrivilege(){
        return ResponseEntity.ok()
                .body(Collections.singletonMap("result", "mock all privilege"));
    }

    @GetMapping("/getEmptyUser")
    public ResponseEntity<?> getEmptyUser(){
        var user = appUserRepository.getEmptyUser()
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return ResponseEntity.ok().body(user);
    }
}
