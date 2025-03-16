package knou.seoul.hanwoori.domain.study.study;

import knou.seoul.hanwoori.domain.study.study.dao.StudyDAO;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.exception.StudyExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class StudyServiceImpl implements StudyService {
    private final StudyDAO StudyDAO;

    /* 저장 */
    @Override
    public long save(@Valid Study study){
        if(isDateRangeValid(study.getStartDate(), study.getEndDate())){
            StudyDAO.save(study);
            return study.getStudyId();
        }
        else {
            System.out.println("시작일이 종료일보다 앞설 수 없습니다.");
            return -1;
        }
    }

    /* 수정 */
    @Override
    public void update(@Valid Study study){
        if(isDateRangeValid(study.getStartDate(), study.getEndDate())) {
            StudyDAO.update(study);
        }
        else System.out.println("시작일이 종료일보다 앞설 수 없습니다.");
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
    public Optional<Study> findById(long studyId) {
        return StudyDAO.findById(studyId); }

    /* 삭제 */
    @Override
    public int delete(long studyId) {
        int cnt = 0;
        Optional<Study> _study = StudyDAO.findById(studyId);
        if(_study.isEmpty()) {
            System.out.println("삭제할 스터디 없음");
        }
        else {
            cnt = StudyDAO.delete(studyId);
        }
        return cnt;
    }

    /* 시작일, 종료일 날짜 체크 */
    public boolean isDateRangeValid(LocalDate startDate, LocalDate endDate) {
        // 종료일이 시작일보다 앞서면 유효하지 않음
        return !endDate.isBefore(startDate);
    }
}
