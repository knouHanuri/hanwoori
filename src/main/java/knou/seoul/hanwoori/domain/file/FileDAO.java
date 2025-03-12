package knou.seoul.hanwoori.domain.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileDAO {
    void save(File file);
    Optional<File> findById(Long id);
    List<File> findAll();
    void delete(Long id);
}
