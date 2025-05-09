package knou.seoul.hanwoori.domain.post.comment;

import knou.seoul.hanwoori.domain.post.comment.dao.PostCommentDAO;
import knou.seoul.hanwoori.domain.post.comment.dto.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentDAO postCommentDAO;

    @Override
    public void save(PostComment postComment) {
        postCommentDAO.save(postComment);
    }

    @Override
    public List<PostComment> findAll() {
        return postCommentDAO.findAll();
    }

    @Override
    public Optional<PostComment> findById(Long id) {
        return postCommentDAO.findById(id);
    }

    @Override
    public void modify(PostComment postComment) {
        postCommentDAO.modify(postComment);
    }

    @Override
    public void delete(Long id) {
        postCommentDAO.delete(id);
    }
}
