package knou.seoul.hanwoori.domain.subject;

import knou.seoul.hanwoori.domain.subject.dao.SubjectDAO;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDAO subjectDAO;

    @Override
    public void save(Subject subject) {
        subjectDAO.save(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectDAO.findAll();
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectDAO.findById(id);
    }

    @Override
    public void modify(Subject subject) {
        subjectDAO.modify(subject);
    }
}
