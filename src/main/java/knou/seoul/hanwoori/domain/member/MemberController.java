package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.member.dto.MemberFormRequestDTO;
import knou.seoul.hanwoori.domain.member.dto.MemberPasswordModifyRequestDTO;
import knou.seoul.hanwoori.domain.signup.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("genders")
    public Member.Gender[] genders() {
        return Member.Gender.values();
    }

    @ModelAttribute("grades")
    public Member.Grade[] grades() {
        return Member.Grade.values();
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("memberFormRequestDTO", new MemberFormRequestDTO());
        return "domain/member/member-form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute MemberFormRequestDTO memberForm, BindingResult bindingResult) {

        //비밀번호 확인
        if (!Objects.equals(memberForm.getPassword(), memberForm.getConfirmPassword())) {
            bindingResult.reject("password.mismatch", "비밀번호가 일치하지 않습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "domain/member/member-form";
        }

        Member member = Member.form(memberForm);
        memberService.save(member);
        Long memberId = member.getMemberId();

        return "redirect:/members/" + memberId;
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "domain/member/member-list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Member> member = memberService.findById(id);
        model.addAttribute("member", member.orElse(null));
        return "domain/member/member-view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Member> member = memberService.findById(id);
        model.addAttribute("member", member.orElse(null));
        return "domain/member/member-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String modify(@PathVariable Long id, @Validated @ModelAttribute Member member,BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "domain/member/member-edit-form";
        }

        memberService.modify(member);
        return "redirect:/members/" + id;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int count = memberService.delete(id);
        if (count == 1) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}/password")
    public String editPasswordForm(@PathVariable Long id, Model model) {
        MemberPasswordModifyRequestDTO memberPasswordModifyRequestDTO = new MemberPasswordModifyRequestDTO();
        memberPasswordModifyRequestDTO.setMemberId(id);
        model.addAttribute("memberPasswordModifyRequestDTO", memberPasswordModifyRequestDTO);
        return "domain/member/member-edit-password";
    }

    /*@PatchMapping("/{id}/password") 405 Method Not Allowed */
    @PostMapping("{id}/password")
    public String editPassword(@PathVariable Long id, @Validated @ModelAttribute MemberPasswordModifyRequestDTO memberPasswordModifyRequestDTO,BindingResult bindingResult) {

        //비밀번호 확인
        if (!Objects.equals(memberPasswordModifyRequestDTO.getNewPassword(), memberPasswordModifyRequestDTO.getConfirmNewPassword())) {
            bindingResult.reject("newPassword.mismatch", "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
        }

        //현재 비밀번호 확인
        if(!memberService.checkOldPasswordMatch(memberPasswordModifyRequestDTO)){
            bindingResult.reject("oldPassword.mismatch", " 현재 비밀번호가 일치하지 않습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "domain/member/member-edit-password";
        }

        memberService.modifyPassword(memberPasswordModifyRequestDTO);

        return "redirect:/members/" + id;
    }
}
