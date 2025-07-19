package knou.seoul.hanwoori.domain.study.study.dto;

import knou.seoul.hanwoori.common.security.InputValidator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySearchRequestDTO {
    private Long subjectId;
    private Study.Status status;
    private String title;

    public boolean isEmpty() {
        return (subjectId == null) &&
                (status == null) &&
                (title == null || title.isBlank());
    }

    public boolean isValid() {
        // subjectId가 음수면 유효하지 않음
        if (subjectId != null && subjectId < 0) return false;

        // title이 너무 길거나, 위험한 스크립트 포함 시
        if (title != null && !title.trim().isEmpty()) {
            String trimmed = title.trim();
            if (trimmed.length() > 100) return false;
            if (InputValidator.containsDangerousContent(trimmed)) return false;
        }

        return true;
    }
}
