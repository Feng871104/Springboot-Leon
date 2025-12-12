package com.leon.springbootleon.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityEventListener {

    @EventListener
    public void handleLogin(InteractiveAuthenticationSuccessEvent event) {
        var user = event.getAuthentication().getName();
        log.info("-->> Login Success: {}", user);
    }

    @EventListener
    public void handleLogout(LogoutSuccessEvent event) {
        var user = event.getAuthentication().getName();
        log.info("-->> Logout Success: {}", user);
    }

}
