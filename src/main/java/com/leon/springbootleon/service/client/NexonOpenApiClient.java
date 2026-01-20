package com.leon.springbootleon.service.client;


import com.leon.springbootleon.model.dto.response.NexonOcidDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface NexonOpenApiClient {

    @GetExchange(value = "/maplestorytw/v1/id")
    NexonOcidDto getOcid(@RequestParam("character_name") String characterName);
}
