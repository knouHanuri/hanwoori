package knou.seoul.hanwoori.domain.signup.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class SignupListDTO {
    Long signupId;
    String MemberName;
    Long memberId;
    String SubjectName;
    Long subjectId;
    Integer year;
    private LocalDateTime createdDate;

}
