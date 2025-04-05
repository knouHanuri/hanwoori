package knou.seoul.hanwoori.domain.signup;

import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupFormRequestDTO;
import knou.seoul.hanwoori.domain.subject.SubjectService;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        SignupFormRequestDTO signupFormRequestDTO = new SignupFormRequestDTO();
        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        signupFormRequestDTO.setMemberId(loginMember.getMemberId());
        model.addAttribute("signupFormRequestDTO",signupFormRequestDTO);
        return "domain/signup/signup-form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute SignupFormRequestDTO signupFormRequestDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "domain/signup/signup-form";
        }

        for(Long subjectId : signupFormRequestDTO.getSubjectIds()){

        }

        signupService.save(signupFormRequestDTO);
        Long signupId = signupFormRequestDTO.getSignupId();

        return "redirect:/signups/" + signupId;
    }

    @GetMapping
    public String list(Model model) {
        List<Signup> signups = signupService.findAll();
        model.addAttribute("signups", signups);
        return "domain/signup/signup-list";
    }
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Signup> signup = signupService.findById(id);
        model.addAttribute("signup", signup.orElse(null));
        return "domain/signup/signup-view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Signup> signup = signupService.findById(id);
        SignupFormRequestDTO signupFormRequestDTO = Signup.form(signup.orElse(new Signup()));
        model.addAttribute("signupFormRequestDTO", signupFormRequestDTO);
        return "domain/signup/signup-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String modify(@PathVariable Long id, @Validated @ModelAttribute SignupFormRequestDTO signupFormRequestDTO,BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "domain/signup/signup-edit-form";
        }

        signupService.modify(signupFormRequestDTO);
        return "redirect:/signups/" + id;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int count = signupService.delete(id);
        if (count == 1) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
