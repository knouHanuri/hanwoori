package knou.seoul.hanwoori.domain.study.study;

import knou.seoul.hanwoori.domain.study.study.dao.StudyDAO;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.exception.StudyExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class StudyServiceImpl implements StudyService {
    private final StudyDAO StudyDAO;

    /* 저장 */
    @Override
    public void save(@Valid Study study){
        //중복 회원 확인
        //validateDuplicateMember(member);
        StudyDAO.save(study);
        //return member.getSeq();
    }

    /* 수정 */
    @Override
    public void update(@Valid Study study){
        StudyDAO.update(study);
    }

    //    private void validateDuplicateMember(Study member){
//        studyMapper.findById(member.getId())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
//    }
//
    /* 전체 조회 */
    @Override
    public List<Study> studyListAll() {
        return StudyDAO.studyListAll();
    }

    /* 일정 수 조회 */
    @Override
    public List<Study> studyListLimited(int limit) { return StudyDAO.studyListLimited(limit); }

    /* studyId로 조회 */
    @Override
    public Optional<Study> findById(long studyId) { return StudyDAO.findById(studyId); }

    /* 삭제 */
    @Override
    public int delete(long studyId) {
        Optional<Study> _study = StudyDAO.findById(studyId);
        if(_study.isEmpty()) {
            throw new StudyExceptionHandler(studyId + "번 스터디 없음");
        }
        return StudyDAO.delete(studyId);
    }
}
