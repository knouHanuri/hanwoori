package knou.seoul.hanwoori.domain.study.studyActivity.dao;

import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StudyActivityDAO {
    void save(StudyActivity studyActivity);
    void update(StudyActivity studyActivity);
    Optional<StudyActivity> findById(long studyActivityId);
    List<StudyActivity> findByStudyId(long studyId);
    List<StudyActivity> studyActivityListAll();
    List<StudyActivity> studyActivityListLimited(int limit);
    int deleteByStudyId(long studyId);
    int delete(long studyActivityId);
}
