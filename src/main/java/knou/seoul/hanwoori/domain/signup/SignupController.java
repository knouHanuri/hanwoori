package knou.seoul.hanwoori.domain.signup;

import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupListDTO;
import knou.seoul.hanwoori.domain.subject.SubjectService;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static knou.seoul.hanwoori.common.SessionConst.LOGIN_MEMBER;

@Controller
@RequestMapping("/signups")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final SubjectService subjectService;

    @ModelAttribute("subjects")
    public List<Subject> subjects() {
        return subjectService.findAll();
    }

    @GetMapping("/new")
    public String newForm(Model model, HttpSession session) {

        Signup signup = new Signup();
        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        signup.setMemberId(loginMember.getMemberId());

        model.addAttribute("signup",signup);
        return "domain/signup/signup-form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute Signup signup, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "domain/signup/signup-form";
        }

        signupService.save(signup);
        Long signupId = signup.getSignupId();

        return "redirect:/signups/" + signupId;
    }


    @GetMapping
    public String list(Model model) {
        List<SignupListDTO> signups = signupService.findAll();
        model.addAttribute("signups", signups);
        return "domain/signup/signup-list";
    }

}
