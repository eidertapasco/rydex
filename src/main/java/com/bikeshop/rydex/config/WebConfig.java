package com.bikeshop.rydex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esta vaina le dice a Spring que las peticiones a /uploads/... busquen en la carpeta local 'uploads/'
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
