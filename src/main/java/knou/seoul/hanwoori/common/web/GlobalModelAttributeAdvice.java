package knou.seoul.hanwoori.common.web;

import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.common.SessionConst;
import knou.seoul.hanwoori.domain.member.dto.Member;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return member != null && member.getGrade() == Member.Grade.admin;
    }
}
