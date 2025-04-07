package knou.seoul.hanwoori.domain.file;

import knou.seoul.hanwoori.domain.file.dto.File;
import knou.seoul.hanwoori.domain.file.dto.FileSource;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    void save(File file);
    Optional<File> findById(Long id);
    List<File> findAll();
    void delete(Long id);
    List<File> findBySourceKindAndId(File.SourceKind sourceKind, Long sourceId);
    File store(MultipartFile file, FileSource fileSource) throws IOException;
    List<File> storeFiles(MultipartFile[] files, FileSource fileSource) throws IOException;
}
