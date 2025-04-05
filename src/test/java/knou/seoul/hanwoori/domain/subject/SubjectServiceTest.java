package knou.seoul.hanwoori.domain.subject;

import knou.seoul.hanwoori.domain.post.dto.Post;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SubjectServiceTest {

    @Autowired
    SubjectService subjectService;

    @Autowired
    SqlSession sqlSession;

    Subject subject;

    @BeforeEach
    public void setUpSubject() {
        subject = new Subject();
        subject.setSubjectName("컴이해");
        subject.setGrade(1);
        subject.setSemester(1);
        subject.setProfessor("누구지");
        subject.setRemark("비고");
    }

    @Test
    @DisplayName("과목 저장")
    @Rollback()
    public void save() {
        //Given

        //When
        subjectService.save(subject);

        //then
        assertThat(subject.getSubjectId()).isNotNull();

    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        int count = subjectService.findAll().size();
        sqlSession.clearCache();

        subjectService.save(subject);
        subjectService.save(subject);

        //When
        List<Subject> subjects = subjectService.findAll();

        //Then
        assertThat(subjects).hasSize(count + 2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById() {

        //Given
        subjectService.save(subject);

        //When
        Optional<Subject> foundSubject = subjectService.findById(subject.getSubjectId());

        //Then
        assertThat(foundSubject.get().getSubjectId()).isEqualTo(subject.getSubjectId());
    }

    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify() {

        //Given
        subjectService.save(subject);
        Optional<Subject> foundSubject = subjectService.findById(subject.getSubjectId());
        foundSubject.get().setSubjectName("수정된과목명");
        foundSubject.get().setGrade(2);
        foundSubject.get().setSemester(2);
        foundSubject.get().setProfessor("정재화교수님");
        foundSubject.get().setRemark("수정된비고");

        //변경확인용 Map
        Map<Function<Subject, Object>, Object> updates = Map.of(
                Subject::getSubjectName, foundSubject.get().getSubjectName(),
                Subject::getGrade, foundSubject.get().getGrade(),
                Subject::getSemester, foundSubject.get().getSemester(),
                Subject::getProfessor, foundSubject.get().getProfessor(),
                Subject::getRemark, foundSubject.get().getRemark()

        );

        //When
        subjectService.modify(foundSubject.get());

        //Then
        Optional<Subject> modifiedSubject = subjectService.findById(foundSubject.get().getSubjectId());
        updates.forEach((getter,expectedValue) ->
                assertThat(expectedValue).isEqualTo(getter.apply(modifiedSubject.get()))
        );
    }

    @Test
    @DisplayName("삭제")
    @Rollback()
    public void delete() {

        //Given
        subjectService.save(subject);

        //When
        subjectService.delete(subject.getSubjectId());
        Optional<Subject> foundSubject = subjectService.findById(subject.getSubjectId());

        //Then
        assertThat(foundSubject).isEmpty();

    }
}
