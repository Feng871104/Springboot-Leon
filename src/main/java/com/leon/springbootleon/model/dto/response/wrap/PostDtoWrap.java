package com.leon.springbootleon.model.dto.response.wrap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDtoWrap {

    private Long subPostId;

    private Long subCategoryId;

    private String subPostName;

    private String subPostPath;

    private String createAt;

    private String updateAt;

}
