package knou.seoul.hanwoori.common.config;

import knou.seoul.hanwoori.common.Interceptor.LoginCheckInterceptor;
import knou.seoul.hanwoori.common.auth.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthProperties authProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(authProperties.getPublicUrls().toArray(new String[0]));

        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns(authProperties.getStrictUrls().toArray(new String[0]));
    }
}
