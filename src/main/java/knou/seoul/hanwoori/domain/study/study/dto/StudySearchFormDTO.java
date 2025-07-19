package knou.seoul.hanwoori.domain.study.study.dto;

import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudySearchFormDTO {
    private List<Subject> subject;
    private Study.Status[] status;
}
