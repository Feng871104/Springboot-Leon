package com.leon.springbootleon.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CookieUtils {

    // Cookie 名稱常數
    public static final String JWT_TOKEN_COOKIE_NAME = "leon-jwt-token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "test-leon-refreshToken";

    // 過期時間常數（秒）
    public static final int JWT_TOKEN_MAX_AGE = 24 * 60 * 60; // 1天
    public static final int REFRESH_TOKEN_MAX_AGE = 7 * 24 * 60 * 60; // 7天
    public static final int COOKIE_EXPIRE_NOW = 0; // 立即過期

    /**
     * 建立基本的安全 Cookie
     */
    public static Cookie createSecureCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // 生產環境設為 true，開發環境可設為 false
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    /**
     * 設置 JWT Token Cookie
     */
    public static void setJwtTokenCookie(HttpServletResponse response, String jwtToken) {
        Cookie jwtCookie = createSecureCookie(JWT_TOKEN_COOKIE_NAME, jwtToken, JWT_TOKEN_MAX_AGE);
        response.addCookie(jwtCookie);
    }

    /**
     * 設置 Refresh Token Cookie
     */
    public static void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshCookie = createSecureCookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken, REFRESH_TOKEN_MAX_AGE);
        response.addCookie(refreshCookie);
    }

    /**
     * 清除 JWT Token Cookie
     */
    public static void clearJwtTokenCookie(HttpServletResponse response) {
        Cookie jwtCookie = createSecureCookie(JWT_TOKEN_COOKIE_NAME, "", COOKIE_EXPIRE_NOW);
        response.addCookie(jwtCookie);
    }

    /**
     * 清除 Refresh Token Cookie
     */
    public static void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshCookie = createSecureCookie(REFRESH_TOKEN_COOKIE_NAME, "", COOKIE_EXPIRE_NOW);
        response.addCookie(refreshCookie);
    }

    /**
     * 清除所有認證相關的 Cookies
     */
    public static void clearAllAuthCookies(HttpServletResponse response) {
        clearJwtTokenCookie(response);
        clearRefreshTokenCookie(response);
    }

    /**
     * 設置完整的登入 Cookies（JWT + Refresh Token）
     */
    public static void setLoginCookies(HttpServletResponse response, String jwtToken, String refreshToken) {
        setJwtTokenCookie(response, jwtToken);
        setRefreshTokenCookie(response, refreshToken);
    }
}
