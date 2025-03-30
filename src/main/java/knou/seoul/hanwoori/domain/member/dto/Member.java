package knou.seoul.hanwoori.domain.member.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    private Long memberId;
    private String loginId;
    private String password;
    private String name;
    private Grade grade;
    private String email;
    private String phoneNumber;
    private Gender gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String studentNo;
    private String remark;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static Member form(MemberFormRequestDTO memberForm) {
        return Member.builder()
                .loginId(memberForm.getLoginId())
                .password(memberForm.getPassword())
                .name(memberForm.getName())
                .grade(Grade.basic) //회원등급 - 기본값: 일반
                .email(memberForm.getEmail())
                .phoneNumber(memberForm.getPhoneNumber())
                .gender(memberForm.getGender())
                .birthdate(memberForm.getBirthdate())
                .studentNo(memberForm.getStudentNo())
                .remark(memberForm.getRemark())
                .build();
    }

    @Getter
    public enum Grade {
        admin("관리자"),
        basic("일반");

        private final String displayName;

        Grade(String displayName) {
            this.displayName = displayName;
        }
    }

    @Getter
    public enum Gender{
        male("남자"),
        female("여자");

        private final String displayName;

        Gender(String displayName) {
            this.displayName = displayName;
        }

    }

}
