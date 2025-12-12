package com.leon.springbootleon.model.dto.response;

import com.leon.springbootleon.model.dto.response.wrap.PostDtoWrap;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostDto {

    private String categoryName;

    private String categoryIcon;

    private String categoryPath;

    private List<PostDtoWrap> posts;


}
