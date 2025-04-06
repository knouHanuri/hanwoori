package knou.seoul.hanwoori.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private Long postId;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private Member member;
    @NotNull(message = "게시글 분류를 선택해주세요.")
    private Category category;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Getter
    public enum Category {
        notice("공지사항"),
        qna("Q&A"),
        free("자유게시판"),
        gallery("갤러리");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }
    }
}
