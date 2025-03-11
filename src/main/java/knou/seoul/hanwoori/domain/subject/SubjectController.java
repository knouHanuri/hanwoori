package knou.seoul.hanwoori.domain.subject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

}
