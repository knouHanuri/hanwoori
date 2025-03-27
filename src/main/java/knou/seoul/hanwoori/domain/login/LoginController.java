package knou.seoul.hanwoori.domain.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class LoginController {

    LoginService loginService;

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
