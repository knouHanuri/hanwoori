package knou.seoul.hanwoori.domain.post.dao;
import knou.seoul.hanwoori.domain.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostDAO {
    void save(Post post);
    List<Post> findAll();
    Optional<Post> findById(Long id);
    void modify(Post post);
}
