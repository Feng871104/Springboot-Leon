package com.leon.springbootleon.config;

import com.leon.springbootleon.service.client.NexonOpenApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value(value = "${nexon.api.key}")
    private String NEXON_API_KEY;

    @Bean(name = "nexonOpenApiHttpServiceProxyFactory")
    public HttpServiceProxyFactory nexonHttpServiceProxyFactory() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://open.api.nexon.com/")
                .defaultHeader("x-nxopen-api-key", NEXON_API_KEY)
                .defaultHeader("accept", "application/json")
                .build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
    }

    @Bean(name = "nexonOpenApiRestClient")
    public NexonOpenApiClient nexonOpenApiRestClient(@Qualifier(value = "nexonOpenApiHttpServiceProxyFactory") HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(NexonOpenApiClient.class);
    }
}
