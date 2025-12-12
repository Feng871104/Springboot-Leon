package com.leon.springbootleon.service;

import com.leon.springbootleon.model.dto.response.PostContentDto;
import com.leon.springbootleon.model.dto.response.PostDto;
import com.leon.springbootleon.model.dto.response.wrap.PostDtoWrap;
import com.leon.springbootleon.model.entity.Post;
import com.leon.springbootleon.model.entity.PostCategory;
import com.leon.springbootleon.repository.PostCategoryRepository;
import com.leon.springbootleon.repository.PostRepository;
import com.leon.springbootleon.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostCategoryRepository postCategoryRepository;

    private final PostRepository postRepository;

    public PostService(PostCategoryRepository postCategoryRepository, PostRepository postRepository) {
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
    }

    public List<PostDto> getAllPost(){
        var postCategories = postCategoryRepository.findAllByEnabledTrueOrderBySortOrderAsc();
        if (postCategories.isEmpty()){
            log.info("Not found any post category");
            return Collections.emptyList();
        }
        var categoryIds = postCategories.stream()
                .map(PostCategory::getId)
                .toList();
        var posts = postRepository.findAllByEnabledTrueAndCategoryIdInOrderBySortOrderAsc(categoryIds);
        var groupPosts = posts.stream()
                .collect(Collectors.groupingBy(Post::getCategoryId, Collectors.toList()));
        return postCategories.stream()
                .map(postCategory -> PostDto.builder()
                        .categoryName(postCategory.getTitle())
                        .categoryIcon(postCategory.getIcon())
                        .categoryPath(postCategory.getPath())
                        .posts(groupPosts.getOrDefault(postCategory.getId(), List.of())
                                .stream()
                                .map( post -> PostDtoWrap.builder()
                                        .subPostId(post.getPostId())
                                        .subCategoryId(post.getCategoryId())
                                        .subPostName(post.getTitle())
                                        .subPostPath(post.getPath())
                                        .updateAt(DateUtils.format(post.getUpdatedAt(), DateUtils.ASIA_TAIPEI))
                                        .createAt(DateUtils.format(post.getCreatedAt(), DateUtils.ASIA_TAIPEI))
                                        .build()
                                )
                                .toList()
                        )
                        .build()
                )
                .toList();
    }

    public PostContentDto getPostContent(Long categoryId, Long postId){
        var post = postRepository.findByCategoryIdAndPostIdAndEnabledTrue(categoryId, postId)
                .orElseThrow(() -> new RuntimeException("Post content not found"));
        return PostContentDto.builder()
                .content(post.getContent())
                .updateTime(post.getUpdatedAt())
                .build();
    }
}
