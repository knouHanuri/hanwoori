package knou.seoul.hanwoori.domain.subject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subject {
    private Long subjectId;
    @NotBlank
    private String subjectName;
    private int grade;
    private int semester;
    private String professor;
    private String remark;

}

