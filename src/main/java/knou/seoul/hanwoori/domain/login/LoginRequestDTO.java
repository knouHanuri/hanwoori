package knou.seoul.hanwoori.domain.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
public class LoginRequestDTO {
    private String loginId;
    private String password;

}
