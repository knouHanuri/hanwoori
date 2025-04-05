package knou.seoul.hanwoori.domain.study.studyActivity;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.study.StudyService;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studyActivity")
public class StudyActivityController {
    private final StudyService studyService;
    private final StudyActivityService studyActivityService;
    private final SubjectService subjectService;

    @GetMapping("/form")
    public String studyActivityForm(Long studyId, @RequestParam(required = false) Long studyActivityId, Model model){
        Optional<Study> optionalStudy = Optional.empty();
        if(studyId != null) {
            optionalStudy = studyService.findById(studyId);
            if(optionalStudy.isPresent()) {
                Study study = optionalStudy.get();
                String subjectName = study.getSubjectName(subjectService);
                model.addAttribute("subjectName", subjectName);
                model.addAttribute("studyName", study.getTitle());
            }
        }

        Optional<StudyActivity> optionalStudyActivity = Optional.empty();
        if(studyActivityId != null) {
            optionalStudyActivity = studyActivityService.findById(studyActivityId);
        }

        StudyActivity activity = optionalStudyActivity
        .orElseGet(() -> {
            StudyActivity newActivity = new StudyActivity();
            newActivity.setStudyId(studyId);  // 임의로 studyId 설정
            return newActivity;
        });

        model.addAttribute("studyActivity", activity);
        return "domain/studyActivity/studyActivity-form";
    }

    @PostMapping("/formSave")
    public String create(@ModelAttribute @Valid StudyActivity studyActivityForm, RedirectAttributes redirectAttributes, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시, 다시 폼으로 돌아감
            return "domain/studyActivity/studyActivity-form";
        }

        //임시. 나중에 로그인한 사용자로 바꿔야 함
        studyActivityForm.setCreatedMemberId(1);

        String msg = "등록";
        if(studyActivityForm.getStudyActivityId() > 0) {
            studyActivityService.update(studyActivityForm);
            msg = "수정";
        }
        else studyActivityService.save(studyActivityForm);

        redirectAttributes.addFlashAttribute("message", String.format("스터디 활동내역이 %s되었습니다.", msg));
        return "redirect:/study/view?studyId=" + studyActivityForm.getStudyId();
    }

    @DeleteMapping("/delete/{studyActivityId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long studyActivityId) {
        int deleteCount = studyActivityService.delete(studyActivityId);
        if(deleteCount == 1) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
