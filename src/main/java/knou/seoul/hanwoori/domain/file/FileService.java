package knou.seoul.hanwoori.domain.file;

import java.util.List;
import java.util.Optional;

public interface FileService {
    void save(File file);
    Optional<File> findById(Long id);
    List<File> findAll();
    void delete(Long id);
}
