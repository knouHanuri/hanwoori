package knou.seoul.hanwoori.domain.post.comment.dao;

import knou.seoul.hanwoori.domain.post.comment.dto.PostComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostCommentDAO {
    void save(PostComment postComment);
    List<PostComment> findAll();
    Optional<PostComment> findById(Long id);
    void modify(PostComment postComment);
}
