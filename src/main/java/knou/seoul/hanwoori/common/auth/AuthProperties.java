package knou.seoul.hanwoori.common.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {
    private List<String> publicUrls;
    private List<String> strictUrls;
}
