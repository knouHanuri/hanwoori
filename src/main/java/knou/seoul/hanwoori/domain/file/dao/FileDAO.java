package knou.seoul.hanwoori.domain.file.dao;

import knou.seoul.hanwoori.domain.file.dto.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileDAO {
    void save(File file);
    Optional<File> findById(Long id);
    List<File> findAll();
    void delete(Long id);
    List<File> findBySourceKindAndId(@Param("sourceKind") File.SourceKind sourceKind, @Param("sourceId") Long sourceId);
}
