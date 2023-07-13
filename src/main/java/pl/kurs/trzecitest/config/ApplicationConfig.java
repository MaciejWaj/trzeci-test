package pl.kurs.trzecitest.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.kurs.trzecitest.security.AppUser;

import java.util.Set;

@Configuration
@EnableJpaAuditing
        (auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public AuditorAware<AppUser> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    @Bean
    public ModelMapper modelMapper(Set<Converter> converters) {
        ModelMapper modelMapper = new ModelMapper();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }
}
