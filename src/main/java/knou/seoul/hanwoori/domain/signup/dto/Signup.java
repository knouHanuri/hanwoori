package knou.seoul.hanwoori.domain.signup.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Signup {

    Long signupId;
    @NotNull
    Long memberId;
    @NotNull
    Long subjectId;
    @NotNull
    Integer year;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
