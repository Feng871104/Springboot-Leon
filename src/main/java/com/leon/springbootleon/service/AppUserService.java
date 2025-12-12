package com.leon.springbootleon.service;

import com.leon.springbootleon.model.dto.request.AppUserDto;
import com.leon.springbootleon.model.entity.AppUser;
import com.leon.springbootleon.repository.AppUserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@Validated
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(@Valid AppUserDto appUserDto){
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(appUserDto, appUser);
        appUser.setUsername(appUserDto.username().trim().toUpperCase());
        appUser.setPassword(passwordEncoder.encode(appUserDto.password()));
        try {
            appUserRepository.save(appUser);
        } catch (Exception e){
            log.error("Save user failed! message: {}", e.getMessage());
        }
    }

    public List<AppUser> getAllUser(){
        return appUserRepository.findAll();
    }
}
