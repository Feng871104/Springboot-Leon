package com.leon.springbootleon.repository;

import com.leon.springbootleon.model.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {

    List<PostCategory> findAllByEnabledTrueOrderBySortOrderAsc();

}
