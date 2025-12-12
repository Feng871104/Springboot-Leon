package com.leon.springbootleon.service;

import com.leon.springbootleon.TestcontainersConfiguration;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
public class TestAppUserService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void testFindAll() {
        var result = appUserService.getAllUser();
        System.out.println(objectMapper.writeValueAsString(result));
        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(() -> CollectionUtils.isNotEmpty(result));
    }
}
