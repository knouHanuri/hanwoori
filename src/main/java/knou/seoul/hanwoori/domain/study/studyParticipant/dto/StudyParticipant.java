package knou.seoul.hanwoori.domain.study.studyParticipant.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyParticipant {
    private long studyParticipantId;
    private long studyId;
    private long memberId;
    private LocalDateTime createdDate;
}
