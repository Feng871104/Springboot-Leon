package com.leon.springbootleon.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostContentDto {

    private String content;

    private Instant updateTime;
}
