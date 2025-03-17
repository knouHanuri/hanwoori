package knou.seoul.hanwoori.domain.subject;

import jakarta.servlet.http.HttpServletRequest;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subject")
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
        return "redirect:/domain/subject/subject-form";
    }
}
