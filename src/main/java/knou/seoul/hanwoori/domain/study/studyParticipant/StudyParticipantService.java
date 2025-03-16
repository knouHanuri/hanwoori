package knou.seoul.hanwoori.domain.study.studyParticipant;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipant;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipantParam;

import java.util.List;
import java.util.Optional;

public interface StudyParticipantService {
    long save(@Valid StudyParticipant studyParticipant);
    Optional<StudyParticipant> findStudyParticipantByIds(StudyParticipantParam studyParticipantParam);
    List<StudyParticipant> studyParticipantListAll();
    List<StudyParticipant> studyParticipantListLimited(int limit);
    int delete(StudyParticipantParam studyParticipantParam);
    StudyParticipantParam createStudyParticipantParam(long studyParticipantId, long studyId, long memberId);
}
