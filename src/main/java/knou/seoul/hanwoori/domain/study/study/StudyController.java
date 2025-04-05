package knou.seoul.hanwoori.domain.study.study;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.studyActivity.StudyActivityService;
import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {
    private final StudyService studyService;
    private final SubjectService subjectService;
    private final StudyActivityService studyActivityService;

    @GetMapping("/list")
    public String studyList(Model model){
        List<Study> studyList = studyService.studyListAll();
        model.addAttribute("status", Study.Status.values());
        model.addAttribute("studyList",studyList);
        return "domain/study/study-list";
    }

    @GetMapping("/form")
    public String studyForm(@RequestParam(required = false) Long studyId, Model model){
        Optional<Study> optionalStudy = Optional.empty();
        if(studyId != null) optionalStudy = studyService.findById(studyId);
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        model.addAttribute("status", Study.Status.values());
        model.addAttribute("study", optionalStudy.orElseGet(Study::new));
        return "domain/study/study-form";
    }

    @PostMapping("/formSave")
    public String create(@ModelAttribute @Valid Study studyForm, RedirectAttributes redirectAttributes, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시, 다시 폼으로 돌아감
            return "domain/study/study-form";
        }

        //study.setEstablishedBy("현재 사용자"); //나중에 현재 로그인 한 사용자로 저장

        String msg = "등록";
        if(studyForm.getStudyId() > 0) {
            studyService.update(studyForm);
            msg = "수정";
        }
        else studyService.save(studyForm);

        redirectAttributes.addFlashAttribute("message", String.format("스터디가 %s되었습니다.", msg));
        //return "redirect:/study/list";
        return "redirect:/";
    }

    @DeleteMapping("/delete/{studyId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long studyId) {
        int deleteCount = studyService.delete(studyId);
        if(deleteCount == 1) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/view")
    public String studyDetails(@RequestParam Long studyId, Model model) {
        // studyId로 특정 스터디 조회
        Optional<Study> optionalStudy = studyService.findById(studyId);

        if (optionalStudy.isPresent()) {
            // 조회한 Study 정보를 모델에 추가
            Study study = optionalStudy.get();
            String subjectName = study.getSubjectName(subjectService);

            model.addAttribute("study", study);
            model.addAttribute("subjectName", subjectName);

            //studyActivity 추가
            List<StudyActivity> studyActivityList = studyActivityService.findByStudyId(studyId);
            model.addAttribute("studyActivity", studyActivityList);

            return "domain/study/study-view"; // Thymeleaf 템플릿 반환
        } else {
            // studyId가 잘못되었을 경우
            model.addAttribute("error", "해당 스터디를 찾을 수 없습니다.");
            return "domain/study/study-list"; // 에러 메시지만 포함된 템플릿 반환
        }
    }
}
