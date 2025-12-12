package com.leon.springbootleon.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RedirectController {

    @GetMapping("/swagger")
    public String root(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        log.info("-->> IP: {} redirect to /swagger-ui/index.html", clientIp);
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping({"/","/backend/**", "/about", "/projects/**", "/post/**"})
    public String forward() {
        return "forward:/index.html";
    }

}
