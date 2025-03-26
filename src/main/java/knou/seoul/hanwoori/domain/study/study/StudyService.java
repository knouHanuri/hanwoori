package knou.seoul.hanwoori.domain.study.study;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.study.dto.Study;

import java.util.List;
import java.util.Optional;

public interface StudyService {
    long save(@Valid Study study);
    void update(@Valid Study study);
    Optional<Study> findById(long studyId);
    List<Study> studyListAll();
    List<Study> studyListLimited(int limit);
    int delete(long studyId);
}
