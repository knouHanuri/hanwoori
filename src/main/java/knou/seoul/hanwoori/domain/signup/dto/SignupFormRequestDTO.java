package knou.seoul.hanwoori.domain.signup.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupFormRequestDTO {
    Long signupId;
    @NotNull
    Long memberId;
    @Size(min = 1,message = "한 과목이상 선택해주세요")
    List<Long> subjectIds;
    @NotNull
    Integer year;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
