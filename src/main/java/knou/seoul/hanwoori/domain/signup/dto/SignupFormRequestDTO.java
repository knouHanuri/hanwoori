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
    @NotNull(message = "수강신청하는 연도를 선택해주세요")
    Integer year;
    @NotNull(message = "수강신청하는 학기를 선택해주세요")
    Integer semester;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
