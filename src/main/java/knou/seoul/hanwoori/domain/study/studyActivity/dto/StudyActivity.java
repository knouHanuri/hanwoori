package knou.seoul.hanwoori.domain.study.studyActivity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import knou.seoul.hanwoori.domain.study.study.StudyService;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class StudyActivity {
    private long studyActivityId;
    private long studyId;

    @NotNull(message = "스터디 날짜 입력해라")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime studyDate;

    @NotBlank(message = "제목입력해라")
    private String title;

    @NotBlank(message = "내용입력해라")
    private String content;

    private int createdMemberId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public String getStudyName(StudyService studyService) {
        Optional<Study> optionalStudy = studyService.findById(studyId);
        return optionalStudy.isPresent() ? optionalStudy.get().getTitle() : "";
    }
}
