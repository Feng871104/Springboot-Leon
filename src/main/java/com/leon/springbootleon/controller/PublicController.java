package com.leon.springbootleon.controller;

import com.leon.springbootleon.annotation.LogExecuteTime;
import com.leon.springbootleon.model.dto.response.CustomApiResponse;
import com.leon.springbootleon.service.PublicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/public", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "公開API")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @LogExecuteTime
    @GetMapping("/helloWorld")
    @Operation(
            summary = "Hello World!",
            description = "哈摟沃德"
    )
    public ResponseEntity<?> HelloWorld() {
        return ResponseEntity.ok().body(new CustomApiResponse(HttpStatus.OK, "Hello World!"));
    }

    @GetMapping("/MasterDegreeInfo")
    public ResponseEntity<?> getMasterDegreeInfo() {
        return ResponseEntity.ok(publicService.getMasterDegreeInfo());
    }

}
