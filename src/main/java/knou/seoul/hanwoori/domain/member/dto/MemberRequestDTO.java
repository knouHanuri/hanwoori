package knou.seoul.hanwoori.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MemberRequestDTO {
    private Long memberId;
    private String loginId;
    private String password;
    private String name;
    private Member.Grade grade;
    private String email;
    private String phoneNumber;
    private Member.Gender gender;
    private LocalDate birthdate;
    private String studentNo;
    private String remark;
}
