package com.leon.springbootleon.controller;

import com.leon.springbootleon.service.JwtTokenService;
import com.leon.springbootleon.utils.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API")
public class AuthController {

    private final JwtTokenService jwtTokenService;

    public AuthController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/token")
    @SecurityRequirement(name = "basicAuth")
    @Operation(
            summary = "Get JWT token",
            description = "使用用戶名和密碼取得 JWT token，並設置 refresh token cookie"
    )
    public ResponseEntity<?> token(Authentication authentication, HttpServletResponse response) {
        log.debug("Generate token from user : {}", authentication.getName());
        String refreshToken = UUID.randomUUID().toString();
        // 使用工具類設置 refresh token cookie
        CookieUtils.setRefreshTokenCookie(response, refreshToken);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("token", jwtTokenService.generateToken(authentication)));
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "使用用戶名和密碼登入，JWT token 會自動設置在 HttpOnly cookie 中"
    )
    public ResponseEntity<?> login(Authentication authentication, HttpServletResponse response) {
        log.debug("User login: {}", authentication.getName());
        // 生成 tokens
        String jwtToken = jwtTokenService.generateToken(authentication);
        String refreshToken = UUID.randomUUID().toString();
        // 使用工具類一次設置所有登入相關的 cookies
        CookieUtils.setLoginCookies(response, jwtToken, refreshToken);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("message", "Login successful"));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "User logout",
            description = "登出並清除相關 cookies"
    )
    public ResponseEntity<?> logout(Authentication authentication, HttpServletResponse response) {
        log.debug("User: {} logout", authentication.getName());
        // 使用工具類清除所有認證相關的 cookies
        CookieUtils.clearAllAuthCookies(response);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("message", "Logout successful"));
    }

}
