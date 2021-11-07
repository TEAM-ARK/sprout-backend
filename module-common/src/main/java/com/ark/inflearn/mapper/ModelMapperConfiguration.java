package com.ark.inflearn.mapper;

import static org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldAccessLevel(AccessLevel.PRIVATE)
            .setFieldMatchingEnabled(true);
        return modelMapper;
    }

}
