package com.leon.springbootleon.repository;

import com.leon.springbootleon.model.dto.projection.EmptyUser;
import com.leon.springbootleon.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByUsername(String username);

    @Query(value = """
                        SELECT username
                              ,18 as ggSize
                        FROM public.users
                        WHERE username = 'GAY'
            """, nativeQuery = true)
    Optional<EmptyUser> getEmptyUser();

}
