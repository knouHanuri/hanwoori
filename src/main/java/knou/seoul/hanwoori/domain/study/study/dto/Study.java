package knou.seoul.hanwoori.domain.study.study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import knou.seoul.hanwoori.domain.subject.SubjectService;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class Study {
    private long studyId;
    private long memberId;
    private long subjectId;

    @NotBlank(message = "제목입력해라")
    private String title;

    @NotNull(message = "상태입력해라")
    private Status status;

    @NotBlank(message = "일정입력해라")
    private String schedule;
    private String goal;

    @NotNull(message = "시작일입력해라")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "마감일입력해라")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String remark;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Getter
    public enum Status{
        active("진행중"),
        complete("완료"),
        pending("중단"),
        ready("준비중");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }
    }

    public String getSubjectName(SubjectService subjectService) {
        Optional<Subject> optionalSubject = subjectService.findById(subjectId);
        return optionalSubject.isPresent() ? optionalSubject.get().getSubjectName() : "";
    }
}
