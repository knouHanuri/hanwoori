package knou.seoul.hanwoori.domain.study.studyActivity;

import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studyActivity")
public class StudyActivityController {
    private final StudyActivityService studyActivityService;

    @GetMapping("/form")
    public String studyActivityForm(@RequestParam(required = false) Long studyActivityId, Model model){
        Optional<StudyActivity> optionalStudyActivity = Optional.empty();
        if(studyActivityId != null) optionalStudyActivity = studyActivityService.findById(studyActivityId);
//        List<Subject> subjects = subjectService.findAll();
//        model.addAttribute("subjects", subjects);
//        model.addAttribute("status", Study.Status.values());
        model.addAttribute("studyActivity", optionalStudyActivity.orElseGet(StudyActivity::new));
        return "domain/studyActivity/studyActivity-form";
    }
}
