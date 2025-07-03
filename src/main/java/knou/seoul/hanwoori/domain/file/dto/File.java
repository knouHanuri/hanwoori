package knou.seoul.hanwoori.domain.file.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class File {
    private Long fileId;
    private SourceKind sourceKind;
    private Long sourceId;
    private String filePath;
    private String originalFileName;
    private String storeFileName;
    private Long fileSize;
    private LocalDateTime createdDate;

    @Getter
    public enum SourceKind{
        post("게시판"),
        study("스터디"),
        member("회원");

        private final String displayName;
        SourceKind(String displayName){
            this.displayName = displayName;
        }
    }
}
