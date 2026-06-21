package com.arpita.campusmarketplace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/icons/**")
                .addResourceLocations(
                        "file:///C:/Users/adiya/OneDrive/Pictures/Lenovo/LenovoNow/icons/"
                );
    }
}