package knou.seoul.hanwoori.domain.subject;

import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "domain/subject/subject-form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Subject subject) {
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
    public String modify(@PathVariable Long id, @ModelAttribute Subject subject) {
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
