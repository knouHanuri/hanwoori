package knou.seoul.hanwoori.domain.file.dto;

public interface FileSource {
    Long getSourceId();
    File.SourceKind getSourceKind();
}
