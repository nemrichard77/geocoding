package hu.uni.eke.b999fa.geocoding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "geocode-configuration")
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class GeocodeConfiguration {
    @Getter private String API_KEY;
}
