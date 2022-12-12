package io.github.CarolinaCedro.POC01.config.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper convert() {
        return new ModelMapper();
    }
}