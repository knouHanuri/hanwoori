package knou.seoul.hanwoori.domain.file;

import knou.seoul.hanwoori.domain.file.dto.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Value("${file.upload.dir}")
    private String fileDir;

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {

        Optional<File> file = fileService.findById(id);
        if(file.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        File findFile = file.orElseGet(File::new);
        Path filePath = Paths.get(findFile.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + findFile.getOriginalFileName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Resource> deleteFile(@PathVariable Long id) {

        Optional<File> optionalFile = fileService.findById(id);

        if (optionalFile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        File file = optionalFile.get();
        java.nio.file.Path filePath = Paths.get(file.getFilePath());

        try {
            // 실제 파일 삭제
            java.nio.file.Files.deleteIfExists(filePath);

            // DB에서 파일 정보 삭제
            fileService.delete(id);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            // 파일 삭제 실패 시 로그 및 에러 응답
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }




}
