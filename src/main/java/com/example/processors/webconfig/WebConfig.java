package com.example.processors.webconfig;

import com.example.processors.processors.models.StringToCustomColorRGBConverter;
import com.example.processors.processors.models.StringToCustomColorRGBConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.format.FormatterRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Setar as configurações para todas as rotas
                .allowedOrigins("http://localhost:3000") // Adicione a origem permitida
                .allowedMethods("GET", "POST") // Métodos permitidos
                .allowedHeaders("*"); // Cabeçalhos permitidos
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCustomColorRGBConverter());
    }
}
