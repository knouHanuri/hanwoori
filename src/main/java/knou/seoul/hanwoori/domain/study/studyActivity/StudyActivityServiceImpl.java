package knou.seoul.hanwoori.domain.study.studyActivity;

import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.study.dao.StudyDAO;
import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;
import knou.seoul.hanwoori.domain.study.studyActivity.dao.StudyActivityDAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class StudyActivityServiceImpl implements StudyActivityService {
    private final StudyDAO studyDAO;
    private final StudyActivityDAO studyActivityDAO;

    @Override
    public long save(@Valid StudyActivity studyActivity) {
        long _studyActivityId = -1;

        Optional<Study> study = studyDAO.findById(studyActivity.getStudyId());
        if(study.isPresent()) {
            studyActivityDAO.save(studyActivity);
            _studyActivityId = studyActivity.getStudyActivityId();
        }
        else System.out.println("스터디 없음");

        return _studyActivityId;
    }

    @Override
    public void update(@Valid StudyActivity studyActivity) {
        //해당 studyActivity > study_id가 달라진걸 먼저 확인 후 study 존재하는지 확인해야함
//        studyActivityDAO.findById(studyActivity.getStudyActivityId())
//                .filter(existingActivity -> Objects.equals(existingActivity.getStudyId(), studyActivity.getStudyId()))
//                .flatMap(existingActivity -> studyDAO.findById(studyActivity.getStudyId())
//                        .map(study -> {
//                            studyActivityDAO.update(studyActivity);
//                            return true; // 성공적인 처리
//                        }))
//                .orElseGet(() -> {
//                    if (!studyActivityDAO.findById(studyActivity.getStudyActivityId()).isPresent()) {
//                        System.out.println("수정할 스터디 활동내역이 없음");
//                    } else if (!Objects.equals(studyActivity.getStudyId(), studyActivity.getStudyId())) {
//                        System.out.println("studyId는 수정불가");
//                    } else {
//                        System.out.println("스터디 없음");
//                    }
//                    return false; // 실패 처리
//                });

        Optional<StudyActivity> _studyActivity = studyActivityDAO.findById(studyActivity.getStudyActivityId());
        if(_studyActivity.isPresent()) {
            if(!Objects.equals(_studyActivity.get().getStudyId(), studyActivity.getStudyId())){
                System.out.println("studyId는 수정불가");
            }
            else{
                Optional<Study> study = studyDAO.findById(studyActivity.getStudyId());
                if(study.isPresent()) {
                    studyActivityDAO.update(studyActivity);
                }
                else System.out.println("스터디 없음");
            }
        }
        else System.out.println("수정할 스터디 활동내역이 없음");
    }

    @Override
    public Optional<StudyActivity> findById(long studyActivityId) {
        return studyActivityDAO.findById(studyActivityId);
    }

    @Override
    public List<StudyActivity> findByStudyId(long studyId) {
        return studyActivityDAO.findByStudyId(studyId);
    }

    @Override
    public List<StudyActivity> studyActivityListAll() {
        return studyActivityDAO.studyActivityListAll();
    }

    @Override
    public List<StudyActivity> studyActivityListLimited(int limit) {
        return studyActivityDAO.studyActivityListLimited(limit);
    }

    @Override
    public int deleteByStudyId(long studyId) {
        return studyActivityDAO.deleteByStudyId(studyId);
    }

    @Override
    public int delete(long studyActivityId) {
        return studyActivityDAO.delete(studyActivityId);
    }
}
