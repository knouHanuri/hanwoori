package knou.seoul.hanwoori.domain.study.studyActivity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StudyActivityController {
    private final StudyActivityService studyActivityService;
}
