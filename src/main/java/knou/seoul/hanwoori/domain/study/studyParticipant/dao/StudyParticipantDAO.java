package knou.seoul.hanwoori.domain.study.studyParticipant.dao;

import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipant;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipantParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StudyParticipantDAO {
    void save(StudyParticipant studyParticipant);
    Optional<StudyParticipant> findStudyParticipantByIds(StudyParticipantParam param);
    List<StudyParticipant> studyParticipantListAll();
    List<StudyParticipant> studyParticipantListLimited(int limit);
    int delete(StudyParticipantParam param);
}
