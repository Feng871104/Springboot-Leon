package com.leon.springbootleon.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomApiResponse {

    private HttpStatus status = HttpStatus.OK;

    private String message = "";

}
