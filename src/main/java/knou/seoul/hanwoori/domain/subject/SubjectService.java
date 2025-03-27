package knou.seoul.hanwoori.domain.subject;

import knou.seoul.hanwoori.domain.subject.dto.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    void save(Subject subject);
    List<Subject> findAll();
    Optional<Subject> findById(Long id);
    void modify(Subject subject);
    int delete(Long id);

}
