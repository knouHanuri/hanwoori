package knou.seoul.hanwoori.domain.study.study.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySearchRequestDTO {
    private Long subjectId;
    private Study.Status status;
    private String title;
}
