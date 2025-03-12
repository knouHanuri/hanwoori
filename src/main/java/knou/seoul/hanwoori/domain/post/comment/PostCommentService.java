package knou.seoul.hanwoori.domain.post.comment;

import knou.seoul.hanwoori.domain.post.comment.dto.PostComment;

import java.util.List;
import java.util.Optional;

public interface PostCommentService {
    void save(PostComment postComment);
    List<PostComment> findAll();
    Optional<PostComment> findById(Long id);
    void modify(PostComment postComment);
}
