package knou.seoul.hanwoori.domain.subject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subject {

    private Long subjectId;

    @NotBlank
    private String subjectName;

    @NotNull(message = "학년을 선택하세요")
    private Integer grade;

    @NotNull(message = "학기를 선택하세요")
    private Integer semester;

    @NotBlank
    private String professor;

    private String remark;

}

