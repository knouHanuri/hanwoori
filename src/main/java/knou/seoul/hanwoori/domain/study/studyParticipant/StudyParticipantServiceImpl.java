package knou.seoul.hanwoori.domain.study.studyParticipant;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.study.dao.StudyDAO;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.studyParticipant.dao.StudyParticipantDAO;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipantParam;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class StudyParticipantServiceImpl implements StudyParticipantService {
    private final StudyDAO studyDAO;
    private final StudyParticipantDAO studyParticipantDAO;

    @Override
    public long save(@Valid StudyParticipant studyParticipant) {
        //TODO: memberId가 member에 있는지
        long studyParticipantId = -1;
        Optional<Study> study = studyDAO.findById(studyParticipant.getStudyId());
        if(study.isPresent()) {
            StudyParticipantParam param = createStudyParticipantParam(
                studyParticipant.getStudyId(),
                studyParticipant.getMemberId()
            );

            Optional<StudyParticipant> _studyParticipant = studyParticipantDAO.findStudyParticipantByIds(param);
            if(_studyParticipant.isPresent()) {
                System.out.println("이미 존재하는 참여자");
            }
            else {
                studyParticipantDAO.save(studyParticipant);
                studyParticipantId = studyParticipant.getStudyParticipantId();
            }
        }
        else {
            System.out.println("연결된 스터디 없음");
        }
        return studyParticipantId;
    }

    @Override
    public Optional<StudyParticipant> findStudyParticipantByIds(StudyParticipantParam studyParticipantParam) {
        return studyParticipantDAO.findStudyParticipantByIds(studyParticipantParam);
    }

    @Override
    public List<StudyParticipant> findStudyParticipantByStudyId(long studyId) {
        return studyParticipantDAO.findStudyParticipantByStudyId(studyId);
    }

    @Override
    public List<StudyParticipant> studyParticipantListAll() {
        return studyParticipantDAO.studyParticipantListAll();
    }

    @Override
    public List<StudyParticipant> studyParticipantListLimited(int limit) {
        return studyParticipantDAO.studyParticipantListLimited(limit);
    }

    @Override
    public int delete(StudyParticipantParam studyParticipantParam) {
        return studyParticipantDAO.delete(studyParticipantParam);
    }

    @Override
    public StudyParticipantParam createStudyParticipantParam(long studyId, long memberId) {
        // 파라미터 객체 생성
        StudyParticipantParam param = new StudyParticipantParam();
        param.setStudyId(studyId);
        param.setMemberId(memberId);

        return param;
    }
}
