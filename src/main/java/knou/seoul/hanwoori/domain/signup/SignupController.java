package knou.seoul.hanwoori.domain.signup;

import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.post.dto.Post;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static knou.seoul.hanwoori.common.SessionConst.LOGIN_MEMBER;

@Controller
@RequestMapping("/signups")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final SubjectService subjectService;
    private final MemberService memberService;

    @ModelAttribute("subjects")
    public List<Subject> subjects() {
        return subjectService.findAll();
    }

    @GetMapping("/new")
    public String newForm(Model model, HttpSession session) {
        SignupFormRequestDTO signupFormRequestDTO = new SignupFormRequestDTO();
        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));
        signupFormRequestDTO.setMemberId(loginMember.orElseGet(Member::new).getMemberId());
        model.addAttribute("signupFormRequestDTO",signupFormRequestDTO);
        return "domain/signup/signup-form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute SignupFormRequestDTO signupFormRequestDTO, BindingResult bindingResult,HttpSession session) {

        if(bindingResult.hasErrors()) {
            return "domain/signup/signup-form";
        }

        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));

        for(Long subjectId : signupFormRequestDTO.getSubjectIds()){
            Subject subject = subjectService.findById(subjectId).orElseGet(Subject::new);
            Signup signup = new Signup();
            signup.setMember(loginMember.orElseGet(Member::new));
            signup.setSubject(subject);
            signup.setYear(signupFormRequestDTO.getYear());
            signup.setSemester(signupFormRequestDTO.getSemester());
            signupService.save(signup);
        }
        return "redirect:/";
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
        model.addAttribute("signup", signup.orElseGet(Signup::new));
        return "domain/signup/signup-view";
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

    //회원 수강신청 기능
    @GetMapping("/members")
    public String signups(Model model, HttpSession session) {
        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));
        List<Signup> signups = signupService.findByMemberIdGroupBy(loginMember.orElseGet(Member::new).getMemberId());
        model.addAttribute("signups", signups);
        return "domain/signup/member-signup-list";
    }

    //회원 수강신청 삭제
    @DeleteMapping("{year}/members/{memberId}")
    public ResponseEntity<Void> deleteMemberSignups(@PathVariable Integer year,@PathVariable Long memberId) {
        int count = signupService.deleteByMemberIdAndYear(memberId,year);
        if (count > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{year}/members/{memberId}")
    public String viewByMemberIdAndYear(@PathVariable Integer year, @PathVariable Long memberId, Model model) {
        List<Signup> signups = signupService.findByMemberIdAndYear(memberId,year);
        SignupFormRequestDTO signupFormRequestDTO = new SignupFormRequestDTO();
        List<Long> subjectIds = new ArrayList<>();
        for(Signup s : signups){
            subjectIds.add(s.getSubject().getSubjectId());
        }
        signupFormRequestDTO.setMemberId(memberId);
        signupFormRequestDTO.setYear(year);
        signupFormRequestDTO.setSubjectIds(subjectIds);
        model.addAttribute("signupFormRequestDTO", signupFormRequestDTO);
        return "domain/signup/signup-view";
    }

    @GetMapping("/{year}/members/{memberId}/edit")
    public String editForm(@PathVariable Integer year,@PathVariable Long memberId, Model model) {
        List<Signup> signups = signupService.findByMemberIdAndYear(memberId,year);
        SignupFormRequestDTO signupFormRequestDTO = new SignupFormRequestDTO();
        List<Long> subjectIds = new ArrayList<>();
        for(Signup s : signups){
            subjectIds.add(s.getSubject().getSubjectId());
        }
        signupFormRequestDTO.setMemberId(memberId);
        signupFormRequestDTO.setYear(year);
        signupFormRequestDTO.setSubjectIds(subjectIds);
        model.addAttribute("signupFormRequestDTO", signupFormRequestDTO);

        return "domain/signup/signup-edit-form";
    }


    @PostMapping("/{year}/members/{memberId}/edit")
    public String modify(@PathVariable Integer year,@PathVariable Long memberId,
                         @Validated @ModelAttribute SignupFormRequestDTO signupFormRequestDTO, BindingResult bindingResult,HttpSession session) {

        if(bindingResult.hasErrors()) {
            return "domain/signup/signup-edit-form";
        }

        //트랜잭션 관리 등 문제점이 잇을듯
        int count = signupService.deleteByMemberIdAndYear(memberId,year);
        if (count > 0) {
            Optional<Member> member = memberService.findById(signupFormRequestDTO.getMemberId());
            for(Long subjectId : signupFormRequestDTO.getSubjectIds()){
                Subject subject = subjectService.findById(subjectId).orElseGet(Subject::new);
                Signup signup = new Signup();
                signup.setMember(member.orElseGet(Member::new));
                signup.setSubject(subject);
                signup.setYear(signupFormRequestDTO.getYear());
                signupService.save(signup);
            }

        }
        return "redirect:/signups/" + year + "/members/" + memberId;
    }
}
