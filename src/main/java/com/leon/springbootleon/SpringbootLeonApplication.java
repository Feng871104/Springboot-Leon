package com.leon.springbootleon;

import com.leon.springbootleon.config.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class})
public class SpringbootLeonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLeonApplication.class, args);
    }

}
