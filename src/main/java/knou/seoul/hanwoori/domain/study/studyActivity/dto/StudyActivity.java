package knou.seoul.hanwoori.domain.study.studyActivity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyActivity {
    private long studyActivityId;

    @NotNull(message = "연결 스터디 없음")
    private long studyId;

    @NotNull(message = "스터디 날짜 입력해라")
    private LocalDateTime studyDate;

    @NotBlank(message = "제목입력해라")
    private String title;

    @NotBlank(message = "내용입력해라")
    private String content;

    private int createdMemberId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
