package knou.seoul.hanwoori.domain.post.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostComment {
    private Long postCommentId;
    private Long postId;
    private Long memberId;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
