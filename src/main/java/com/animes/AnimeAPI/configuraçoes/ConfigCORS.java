package com.animes.AnimeAPI.configuraçoes;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigCORS implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica CORS a todos os endpoints
                .allowedOrigins("http://127.0.0.1:5500") // Permita apenas sua origem frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permita os métodos HTTP necessários
                .allowedHeaders("*") // Permita todos os cabeçalhos
                .allowCredentials(true); // Se você estiver usando cookies ou autenticação baseada em sessão
    }
}