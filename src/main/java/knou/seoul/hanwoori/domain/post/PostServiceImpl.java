package knou.seoul.hanwoori.domain.post;

import knou.seoul.hanwoori.domain.post.dao.PostDAO;
import knou.seoul.hanwoori.domain.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;

    @Override
    public void save(Post post) {
        postDAO.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postDAO.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postDAO.findById(id);
    }

    @Override
    public void modify(Post post) {
        postDAO.modify(post);
    }

    @Override
    public int delete(Long id) {
        return postDAO.delete(id);
    }

    @Override
    public List<Post> findByCategory(Post.Category category) {
        return postDAO.findByCategory(category);
    }


}
