package knou.seoul.hanwoori.domain.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(Model model) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        model.addAttribute("loginRequestDTO", loginRequestDTO);
        return "domain/login/login-form";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginRequestDTO loginRequestDTO, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "domain/login/login-form";
        }

        Member loginMember = loginService.login(loginRequestDTO);
        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "domain/login/login-form";
        }

        //로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute("loginMember",loginMember);

        return "redirect:/";
    }
/*
    SecurityConfig 사용
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
*/
}
