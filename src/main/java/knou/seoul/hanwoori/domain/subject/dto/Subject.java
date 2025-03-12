package knou.seoul.hanwoori.domain.subject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subject {
    private Long subjectId;
    private String subjectName;
    private int grade;
    private int semester;
    private String professor;
    private String remark;

}

