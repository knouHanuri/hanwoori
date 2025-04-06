package knou.seoul.hanwoori.domain.study.studyParticipant;

import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static knou.seoul.hanwoori.common.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studyParticipant")
public class StudyParticipantController {
    private final StudyParticipantService studyParticipantService;

    @PostMapping("/save/{studyId}")
    public String create(@PathVariable Long studyId, HttpSession session)
    {
        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        if(loginMember != null){
            StudyParticipant studyParticipant = new StudyParticipant();
            studyParticipant.setStudyId(studyId);
            studyParticipant.setMemberId(loginMember.getMemberId());
            studyParticipantService.save(studyParticipant);
        }

        return "redirect:/";
    }

    @DeleteMapping("/delete/{studyId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long studyId, HttpSession session) {
        int deleteCount = 0;

        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        if(loginMember != null){
            deleteCount = studyParticipantService.delete(studyParticipantService.createStudyParticipantParam(studyId, loginMember.getMemberId()));
        }

        if(deleteCount == 1) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
