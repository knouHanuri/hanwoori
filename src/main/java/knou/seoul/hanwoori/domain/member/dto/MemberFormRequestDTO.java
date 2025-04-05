package knou.seoul.hanwoori.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberFormRequestDTO {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    private Member.Gender gender;
    private LocalDate birthdate;
    private String studentNo;
    private String remark;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
