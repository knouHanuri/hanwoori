package knou.seoul.hanwoori.common.config;

import knou.seoul.hanwoori.common.Interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/"
                        , "/login"
                        , "/logout"
                        , "/error"
                        , "/admin/**"
                        , "/about/**"
                        , "/members/**"
                        //, "/posts/**"
                        , "/study/**"
                        //, "/signups/**"
                        , "/subjects/**"
                        , "/common/**"
                        , "/css/**"
                        , "/images/**"
                        , "/js/**"
                );

    }
}
