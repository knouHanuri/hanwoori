package knou.seoul.hanwoori.domain.study.study;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.studyActivity.StudyActivityService;
import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;
import knou.seoul.hanwoori.domain.study.studyParticipant.StudyParticipantService;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipant;
import knou.seoul.hanwoori.domain.subject.SubjectService;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static knou.seoul.hanwoori.common.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {
    private final StudyService studyService;
    private final SubjectService subjectService;
    private final StudyActivityService studyActivityService;
    private final StudyParticipantService studyParticipantService;

    @GetMapping("/list")
    public String studyList(Model model, HttpSession session){
        List<Study> studyList = studyService.studyListAll();

        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        if(loginMember != null) {
            model.addAttribute("memberId", loginMember.getMemberId());
        }

        model.addAttribute("status", Study.Status.values());
        model.addAttribute("studyList",studyList);
        return "domain/study/study-list";
    }

    @GetMapping("/form")
    public String studyForm(@RequestParam(required = false) Long studyId, Model model, HttpSession session){
        Optional<Study> optionalStudy = Optional.empty();
        if(studyId != null) optionalStudy = studyService.findById(studyId);
        List<Subject> subjects = subjectService.findAll();

        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        model.addAttribute("memberId", loginMember.getMemberId());

        model.addAttribute("subjects", subjects);
        model.addAttribute("status", Study.Status.values());
        model.addAttribute("study", optionalStudy.orElseGet(Study::new));
        return "domain/study/study-form";
    }

    @PostMapping("/formSave")
    public String create(@ModelAttribute @Valid Study studyForm, RedirectAttributes redirectAttributes, BindingResult bindingResult, HttpSession session)
    {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시, 다시 폼으로 돌아감
            return "domain/study/study-form";
        }

        //세션 member 저장
        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        studyForm.setMemberId(loginMember.getMemberId());

        String msg = "등록";
        if(studyForm.getStudyId() > 0) {
            studyService.update(studyForm);
            msg = "수정";
        }
        else studyService.save(studyForm);

        redirectAttributes.addFlashAttribute("message", String.format("스터디가 %s되었습니다.", msg));
        return "redirect:/study/list";
        //return "domain/study/study-list";
    }

    @DeleteMapping("/delete/{studyId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long studyId) {
        studyParticipantService.deleteByStudyId(studyId);
        studyActivityService.deleteByStudyId(studyId);

        int deleteCount = studyService.delete(studyId);
        if(deleteCount == 1) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/view")
    public String studyDetails(@RequestParam Long studyId, Model model, HttpSession session) {
        // studyId로 특정 스터디 조회
        Optional<Study> optionalStudy = studyService.findById(studyId);

        if (optionalStudy.isPresent()) {
            // 조회한 Study 정보를 모델에 추가
            Study study = optionalStudy.get();
            String subjectName = study.getSubjectName(subjectService);

            model.addAttribute("study", study);
            model.addAttribute("subjectName", subjectName);

            Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
            if(loginMember != null) {
                model.addAttribute("memberId", loginMember.getMemberId());

                Optional<StudyParticipant> optionalStudyParticipant = studyParticipantService.findStudyParticipantByIds(
                    studyParticipantService.createStudyParticipantParam(studyId, loginMember.getMemberId())
                );

                model.addAttribute("isParticipantStudy", optionalStudyParticipant.isPresent());
            }

            //studyActivity 추가
            List<StudyActivity> studyActivityList = studyActivityService.findByStudyId(studyId);
            model.addAttribute("studyActivity", studyActivityList);

            //studyParticipant 추가
            List<StudyParticipant> studyParticipantList = studyParticipantService.findStudyParticipantByStudyId(studyId);
            model.addAttribute("studyParticipant", studyParticipantList);

            return "domain/study/study-view"; // Thymeleaf 템플릿 반환
        } else {
            // studyId가 잘못되었을 경우
            model.addAttribute("error", "해당 스터디를 찾을 수 없습니다.");
            return "domain/study/study-list"; // 에러 메시지만 포함된 템플릿 반환
        }
    }
}
