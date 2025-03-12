package knou.seoul.hanwoori.domain.study.studyParticipant.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudyParticipant {
    private int studyId;
    private int subjectId;
    private String title;
    private int status;
    private String schedule;
    private String goal;
    private Date startDate;
    private Date endDate;
    private String remark;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;



}
