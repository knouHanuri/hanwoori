package knou.seoul.hanwoori.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MemberPasswordRequestDTO {
    private Long memberId;
    private String oldPassword;
    private String newPassword;
}
