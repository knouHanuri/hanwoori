package knou.seoul.hanwoori.domain.study.study.dao;

import knou.seoul.hanwoori.domain.study.study.dto.Study;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StudyDAO {
    void save(Study study);
    void update(Study study);
    Optional<Study> findById(long studyId);
    List<Study> studyListAll();
    List<Study> studyListLimited(int limit);
    int delete(long studyId);

    //입력
    //스터디id, 제목, 상태, 스케쥴, 시작일 필수?

    //수정
    //처음 요청시 해당 스터디 있는지 확인
    //입력시와 동일하게 필수항목 확인

    //조회

    //삭제
    //처음 요청시 해당 스터디 있는지 확인
    //스터디 내역, 참여자 모두 삭제된다는 alert(있을경우)
}
