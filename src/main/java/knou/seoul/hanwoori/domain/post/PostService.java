package knou.seoul.hanwoori.domain.post;
import knou.seoul.hanwoori.domain.post.dto.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    void save(Post post);
    List<Post> findAll();
    Optional<Post> findById(Long id);
    void modify(Post post);
    int delete(Long id);
    List<Post> findByCategory(Post.Category category);
}
