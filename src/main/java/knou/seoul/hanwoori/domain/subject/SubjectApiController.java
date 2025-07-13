package knou.seoul.hanwoori.domain.subject;

import knou.seoul.hanwoori.domain.subject.dto.Subject;
import knou.seoul.hanwoori.domain.subject.dto.SubjectResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectApiController {

    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectResponseDTO> filter(
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) Integer semester) {

        List<Subject> subjects;

        if (grade != null && semester != null) {
            subjects = subjectService.findByGradeAndSemester(grade, semester);
        } else if (grade != null) {
            subjects = subjectService.findByGrade(grade);
        } else if (semester != null) {
            subjects = subjectService.findBySemester(semester);
        } else {
            subjects = subjectService.findAll(); // 혹은 빈 리스트 반환
        }

        return subjects.stream()
                .map(s -> new SubjectResponseDTO(s.getSubjectId(), s.getSubjectName()))
                .toList();
    }

    public record SubjectResponseDTO(Long subjectId, String subjectName) {}

}
