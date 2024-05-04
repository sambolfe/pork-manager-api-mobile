package br.csi.porkManagerApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PorkManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PorkManagerApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer configure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Permite requisições apenas do seu frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Permite os métodos HTTP
                        .allowedHeaders("*"); // Permite todos os cabeçalhos
            }
        };
    }
}

