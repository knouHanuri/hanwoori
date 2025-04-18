package knou.seoul.hanwoori.domain.study.studyActivity;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;

import java.util.List;
import java.util.Optional;

public interface StudyActivityService {
    long save(@Valid StudyActivity studyActivity);
    void update(@Valid StudyActivity studyActivity);
    List<StudyActivity> findByStudyId(long studyId);
    Optional<StudyActivity> findById(long studyActivityId);
    List<StudyActivity> studyActivityListAll();
    List<StudyActivity> studyActivityListLimited(int limit);
    int deleteByStudyId(long studyId);
    int delete(long studyActivityId);
}
