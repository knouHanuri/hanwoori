package knou.seoul.hanwoori.domain.signup.dto;

import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signup {
    Long signupId;
    Member member;
    Subject subject;
    Integer year;
    Integer semester;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static SignupFormRequestDTO form(Signup signup) {
        return SignupFormRequestDTO.builder()
                .signupId(signup.getSignupId())
                .memberId(signup.getMember().getMemberId())
                //.subjectIds(signup.getSubject().getSubjectId())
                .year(signup.getYear())
                .semester(signup.getSemester())
                .createdDate(signup.getCreatedDate())
                .updatedDate(signup.getUpdatedDate())
                .build();
    }

}
