package com.leon.springbootleon.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(schema = "leon_web", name = "users")
public class AppUser implements Serializable {

    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String password;

    private String email;

    private boolean enabled = true;

    private boolean locked = false;

    private String role = "USER";

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}

