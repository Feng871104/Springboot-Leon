package com.leon.springbootleon.repository;

import com.leon.springbootleon.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByEnabledTrueAndCategoryIdInOrderBySortOrderAsc(List<Long> categoryIds);

    Optional<Post> findByCategoryIdAndPostIdAndEnabledTrue(Long categoryId, Long postId);

}
