package com.leon.springbootleon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.sql.DataSource;

@Configuration
@Profile("!test")
public class PostgresDataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String encryptedPassword;

    @Value("${leon.secret.key}")
    private String key;

    @Value("${leon.secret.salt}")
    private String salt;

    @Primary
    @Bean(name = "postgresDataSource")
    public DataSource dataSource() {
        TextEncryptor encryptor = Encryptors.text(key, salt);
        String decrypted = encryptor.decrypt(encryptedPassword.replace("ENC(", "").replace(")", "").strip());
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(decrypted)
                .build();
    }
}
