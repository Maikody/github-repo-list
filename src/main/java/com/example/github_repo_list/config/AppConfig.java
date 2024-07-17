package com.example.github_repo_list.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<AcceptHeaderFilter> headerFilter() {
        FilterRegistrationBean<AcceptHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AcceptHeaderFilter(objectMapper));
        return registrationBean;
    }

}
