package knou.seoul.hanwoori.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private Long postId;
    private String title;
    private Long memberId;
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
