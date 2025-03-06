package knou.seoul.hanwoori.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Member {
    private Long memberId;
    private String loginId;
    private String password;
    private String name;
    private MemberGrade memberGrade;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate birthdate;
    private String studentNo;
    private String remark;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Getter
    public enum MemberGrade {
        admin("관리자"),
        basic("일반");

        private final String displayName;

        MemberGrade(String displayName) {
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
