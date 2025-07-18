package knou.seoul.hanwoori.domain.subject.dao;

import knou.seoul.hanwoori.domain.subject.dto.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SubjectDAO {
    void save(Subject subject);
    List<Subject> findAll();
    Optional<Subject> findById(Long id);
    void modify(Subject subject);
    int delete(Long id);
    List<Subject> findByGradeAndSemester(Integer grade, Integer semester);
    List<Subject> findByGrade(Integer grade);
    List<Subject> findBySemester(Integer semester);
}
