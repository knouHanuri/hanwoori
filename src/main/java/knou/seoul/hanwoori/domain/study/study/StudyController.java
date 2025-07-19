package knou.seoul.hanwoori.domain.study.study;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.study.dto.StudySearchFormDTO;
import knou.seoul.hanwoori.domain.study.study.dto.StudySearchRequestDTO;
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

import java.util.Comparator;
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
    public String studyList(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "10") int pageSize,
                            Model model, HttpSession session){

        //페이징
        PageInfo<Study> pageInfo = studyService.studyListAll(pageNum, pageSize);
        pageInfo.setPages(Math.max(pageInfo.getPages(), 1));
        model.addAttribute("pageInfo", pageInfo);

        //검색박스
        StudySearchFormDTO searchFormDTO = new StudySearchFormDTO();

        List<Subject> subjects = subjectService.findAll();

        subjects.sort(Comparator
                .comparing(Subject::getGrade)
                .thenComparing(Subject::getSemester)
                .thenComparing(Subject::getSubjectName));

        searchFormDTO.setSubject(subjects);
        searchFormDTO.setStatus(Study.Status.values());
        model.addAttribute("searchFormDTO", searchFormDTO);

        //로그인
        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
        if(loginMember != null) {
            model.addAttribute("memberId", loginMember.getMemberId());
        }

        //model.addAttribute("status", Study.Status.values());
        //model.addAttribute("studyList",studyList);
        return "domain/study/study-list";
    }

    @GetMapping("/form")
    public String studyForm(@RequestParam(required = false) Long studyId, Model model, HttpSession session){
        Optional<Study> optionalStudy = Optional.empty();
        if(studyId != null) optionalStudy = studyService.findById(studyId);
        List<Subject> subjects = subjectService.findAll();

        subjects.sort(Comparator
                .comparing(Subject::getGrade)
                .thenComparing(Subject::getSemester)
                .thenComparing(Subject::getSubjectName));

        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));
        model.addAttribute("memberId", loginMember.orElseGet(Member::new).getMemberId());

        if(model.getAttribute("memberId") == null) return "redirect:/study/list";

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
        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));
        studyForm.setMemberId(loginMember.orElseGet(Member::new).getMemberId());

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

    @GetMapping("/search")
    public String studySearch(@ModelAttribute StudySearchRequestDTO request, Model model){

//        PageInfo<Study> pageInfo = studyService.studyListAll(pageNum, pageSize);
//        pageInfo.setPages(Math.max(pageInfo.getPages(), 1));
//        model.addAttribute("pageInfo", pageInfo);
//
//        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);
//        if(loginMember != null) {
//            model.addAttribute("memberId", loginMember.getMemberId());
//        }

        //model.addAttribute("status", Study.Status.values());
        //model.addAttribute("studyList",studyList);
        return "domain/study/study-list";
    }
}
