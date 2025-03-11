package knou.seoul.hanwoori.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

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
}
