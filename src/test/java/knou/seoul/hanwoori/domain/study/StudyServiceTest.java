package knou.seoul.hanwoori.domain.study;

import knou.seoul.hanwoori.domain.study.study.StudyService;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
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

    @Nested
    class InputTest {
        @Test
        @DisplayName("성공")
        @Transactional
        public void save_success() {
            studyService.save(study);
        }

        @Test
        @DisplayName("실패 > 필수값 null 입력")
        @Transactional
        public void save_null() {
            study.setTitle(null);
            studyService.save(study);
        }

        @Test
        @DisplayName("실패 > 필수값 공백 입력")
        @Transactional
        public void save_blank() {
            study.setTitle("");
            studyService.save(study);
        }

        @Test
        @DisplayName("성공 > 스터디 신규 입력(종료일이 시작일보다 앞) > '시작일이 종료일보다 앞설 수 없습니다.' 출력")
        @Transactional
        public void save_invalidValue() {
            //시작일이 종료일보다 앞설 수 없습니다. 출력
            study.setStartDate(LocalDate.of(2025, 3, 1));
            study.setEndDate(LocalDate.of(2024, 12, 1));
            studyService.save(study);
        }
    }

    @Nested
    class updateTest{
        // TODO: 기존에 입력되어있던 스터디의 작성자와 수정 작성자가 다를 경우 수정 실패(memberId가 다를때) > 로그인 아이디 가져올 수 있을때

        @Test
        @DisplayName("성공")
        @Transactional
        public void update_success() {
            study.setTitle("test222");
            studyService.update(study);
        }

        @Test
        @DisplayName("실패 > 필수값 null 입력")
        @Transactional
        public void update_null() {
            study.setTitle(null);
            studyService.update(study);
        }

        @Test
        @DisplayName("실패 > 필수값 공백 입력")
        @Transactional
        public void update_blank() {
            study.setTitle("");
            studyService.update(study);
        }

        @Test
        @DisplayName("성공 > 스터디 수정(종료일이 시작일보다 앞) > '시작일이 종료일보다 앞설 수 없습니다.' 출력")
        @Transactional
        public void update_invalidValue() {
            //시작일이 종료일보다 앞설 수 없습니다. 출력
            study.setStartDate(LocalDate.of(2025, 3, 1));
            study.setEndDate(LocalDate.of(2024, 12, 1));
            studyService.update(study);
        }
    }

    @Nested
    class deleteTest {
        //TODO: 해당 스터디에 연결된 studyActivity, studyParticipant가 있을 경우 삭제 불가
        //TODO: 입력자와 다른 사용자가 삭제 시도시 삭제 불가 > 로그인 아이디 가져올 수 있을때
        
        long _studyId = 0;
        @BeforeEach
        public void saveStudy() {
            _studyId = studyService.save(study);
        }

        @AfterEach
        public void deleteStudy() {
            studyService.delete(study.getStudyId());
        }

        @Test
        @DisplayName("성공")
        @Transactional
        public void delete_success() {
            System.out.println(_studyId);
            if(studyService.delete(study.getStudyId()) > 0){
                System.out.println("삭제 성공");
            };
        }
    }
}
