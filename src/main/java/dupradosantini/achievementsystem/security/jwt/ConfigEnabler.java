package dupradosantini.achievementsystem.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtConfig.class)
public class ConfigEnabler {

    @Autowired
    public ConfigEnabler(JwtConfig jwtConfig) {
    }
}
