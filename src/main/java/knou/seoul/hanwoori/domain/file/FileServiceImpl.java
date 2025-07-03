package knou.seoul.hanwoori.domain.file;

import knou.seoul.hanwoori.domain.file.dao.FileDAO;
import knou.seoul.hanwoori.domain.file.dto.File;
import knou.seoul.hanwoori.domain.file.dto.FileSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

    @Value("${file.upload.dir}")
    private String fileDir;

    @Override
    public void save(File file) {
        fileDAO.save(file);
    }

    @Override
    public Optional<File> findById(Long id) {
        return fileDAO.findById(id);
    }

    @Override
    public List<File> findAll() {
        return fileDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        fileDAO.delete(id);
    }

    @Override
    public List<File> findBySourceKindAndId(File.SourceKind sourceKind, Long sourceId) {
        return fileDAO.findBySourceKindAndId(sourceKind, sourceId);
    }

    public File store(MultipartFile file, FileSource fileSource) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        Path folderPath = Paths.get(fileDir + fileSource.getSourceKind());

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        Path fullPath = folderPath.resolve(storeFilename);

        file.transferTo(new java.io.File(fullPath.toString()));

        File storedFile = new File();
        storedFile.setSourceKind(fileSource.getSourceKind());
        storedFile.setSourceId(fileSource.getSourceId());
        storedFile.setStoreFileName(storeFilename);
        storedFile.setOriginalFileName(originalFilename);
        storedFile.setFilePath(fullPath.toString());
        storedFile.setFileSize(file.getSize());
        fileDAO.save(storedFile);
        return storedFile;
    }

    public List<File> storeFiles(MultipartFile[] files, FileSource fileSource) throws IOException {
        List<File> storedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            storedFiles.add(store(file, fileSource));
        }
        return storedFiles;
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String getExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
