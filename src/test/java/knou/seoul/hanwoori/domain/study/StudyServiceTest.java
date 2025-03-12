package knou.seoul.hanwoori.domain.study;

import knou.seoul.hanwoori.domain.study.study.StudyService;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class StudyServiceTest {
    @Autowired
    StudyService studyService;

    Study study;
    @BeforeEach
    public void setUpStudy() {
        study = new Study();
        study.setMemberId(1);
        study.setSubjectId(1);
        study.setTitle("test");
        study.setStatus(Study.Status.active);
        study.setSchedule("매주 금요일 저녁 7시");
        study.setStartDate(LocalDate.of(2024,1,1));
        study.setEndDate(LocalDate.of(2024,3,1));
    }

    @Test
    @DisplayName("성공 > 스터디 신규 입력")
    @Rollback()
    public void save_success(){
        studyService.save(study);
    }

    @Test
    @DisplayName("실패 > 스터디 신규 입력(필수값 null 입력)")
    @Rollback()
    public void save_null(){
        study.setTitle(null);
        studyService.save(study);
    }

    @Test
    @DisplayName("실패 > 스터디 신규 입력(필수값 공백 입력)")
    @Rollback()
    public void save_blank(){
        study.setTitle("");
        studyService.save(study);
    }

    @Test
    @DisplayName("스터디 신규 입력")
    @Rollback()
    public void save_invalidValue(){
        study.setStartDate(LocalDate.of(2024,1,1));
        study.setEndDate(LocalDate.of(2024,1,1));
        studyService.save(study);
    }
}
