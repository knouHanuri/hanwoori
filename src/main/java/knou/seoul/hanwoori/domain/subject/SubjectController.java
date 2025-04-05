package knou.seoul.hanwoori.domain.subject;

import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @ModelAttribute("grades")
    public Map<String, String> grades() {
        Map<String, String> grades = new LinkedHashMap<>();
        grades.put("1학년", "1");
        grades.put("2학년", "2");
        grades.put("3학년", "3");
        grades.put("4학년", "4");
        return grades;
    }

    @ModelAttribute("semesters")
    public Map<String, String> semesters() {
        Map<String, String> semesters = new LinkedHashMap<>();
        semesters.put("1학기", "1");
        semesters.put("2학기", "2");
        return semesters;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "domain/subject/subject-form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute Subject subject, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "domain/subject/subject-form";
        }

        subjectService.save(subject);
        Long subjectId = subject.getSubjectId();

        return "redirect:/subjects/" + subjectId;
    }

    @GetMapping
    public String list(Model model) {
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        return "domain/subject/subject-list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Subject> subject = subjectService.findById(id);
        model.addAttribute("subject", subject.orElse(null));
        return "domain/subject/subject-view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Subject> subject = subjectService.findById(id);
        model.addAttribute("subject", subject.orElse(null));
        return "domain/subject/subject-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String modify(@PathVariable Long id, @Validated @ModelAttribute Subject subject,BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "domain/subject/subject-edit-form";
        }

        subjectService.modify(subject);
        return "redirect:/subjects/" + id;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int count = subjectService.delete(id);
        if (count == 1) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
