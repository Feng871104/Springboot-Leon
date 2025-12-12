package com.leon.springbootleon.controller;

import com.leon.springbootleon.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Post API")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPost(){
        return ResponseEntity.ok().body(postService.getAllPost());
    }

    @GetMapping("/content")
    public ResponseEntity<?> getPostContent(@RequestParam @NotNull Long categoryId,
                                            @RequestParam @NotNull Long postId){
        return ResponseEntity.ok().body(postService.getPostContent(categoryId, postId));
    }
}
