package knou.seoul.hanwoori.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 요청 허용
                );
        // 로그아웃 URL 설정
        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")  // 로그아웃 URL 설정
                        .logoutSuccessUrl("/")  // 로그아웃 성공 후 리디렉션할 URL 설정
                        .invalidateHttpSession(true)  // 세션 무효화
                        .clearAuthentication(true)    // 인증 정보 제거
                        .permitAll()
                );


        // csrf : 사이트 위변조 방지 설정 (스프링 시큐리티에는 자동으로 설정 되어 있음)
        // csrf기능 켜져있으면 post 요청을 보낼때 csrf 토큰도 보내줘야 로그인 진행됨 !
        // 개발단계에서만 csrf 잠시 꺼두기
        http
                .csrf((auth) -> auth.disable());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
