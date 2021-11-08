package com.ark.inflearn.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final HttpLogInterceptor httpLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
            .addInterceptor(httpLogInterceptor)
            .excludePathPatterns("/docs/**", "images/**", "/js/**", "/css/**", "/favicon.ico")
            .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/")
            .setCachePeriod(86400)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }

}
