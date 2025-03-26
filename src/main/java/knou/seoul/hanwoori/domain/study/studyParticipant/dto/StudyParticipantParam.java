package knou.seoul.hanwoori.domain.study.studyParticipant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyParticipantParam {
    private long studyParticipantId;
    private long studyId;
    private long memberId;
}
