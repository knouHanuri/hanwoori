package knou.seoul.hanwoori.domain.study;

import knou.seoul.hanwoori.domain.study.study.StudyService;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.studyActivity.StudyActivityService;
import knou.seoul.hanwoori.domain.study.studyActivity.dto.StudyActivity;
import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Transactional
public class StudyActivityServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    StudyService studyService;
    @Autowired
    StudyActivityService studyActivityService;

    Member member;

    long _studyId = 0;
    Study study;

    long _studyActivityId = 0;
    StudyActivity studyActivity;

    @BeforeEach
    public void setUpStudyActivity() {
        if(memberService.findById(1L).isEmpty()) {
            member = new Member();
            member.setLoginId("id");
            member.setPassword("pwd");
            member.setGrade(Member.Grade.basic);
            member.setName("신원미상");
            member.setEmail("email@email.com");
            member.setPhoneNumber("1234567890");
            member.setGender(Member.Gender.male);
            member.setBirthdate(LocalDate.of(1988, 11, 12));
            member.setStudentNo("studentNo");
            member.setRemark("비고");
            memberService.save(member);
        }

        study = new Study();
        study.setMemberId(1);
        study.setSubjectId(1);
        study.setTitle("test");
        study.setStatus(Study.Status.active);
        study.setSchedule("매주 금요일 저녁 7시");
        study.setStartDate(LocalDate.of(2024,1,1));
        study.setEndDate(LocalDate.of(2024,3,1));
        _studyId = studyService.save(study);

        studyActivity = new StudyActivity();
        studyActivity.setStudyId(_studyId);
        //studyActivity.setStudyDate(LocalDateTime.of(2024,5,11,12,30));
        studyActivity.setTitle("test");
        studyActivity.setContent("상세한 활동 내역");
        studyActivity.setCreatedMemberId(1) ;
    }

    @AfterEach
    public void deleteStudy() {
        studyService.delete(_studyId);
    }

    @Nested
    class InputTest {
        //TODO: 사용자 비교 추가(아이디가 member에 있는지)
        //TODO: 입력자가 스터디 참여자가 아니면 입력불가
        
        @Test
        @DisplayName("성공")
        public void save_success() {
            studyActivityService.save(studyActivity);
        }

        @Test
        @DisplayName("실패 > 필수값 null 입력")
        public void save_null() {
            studyActivity.setTitle(null);
            studyActivityService.save(studyActivity);
        }

        @Test
        @DisplayName("실패 > 필수값 공백 입력")
        public void save_blank() {
            studyActivity.setTitle("");
            studyActivityService.save(studyActivity);
        }

        @Test
        @DisplayName("성공 > 상위 스터디 없음(로그에 실패)")
        public void save_studyNotExists() {
            studyActivity.setStudyId(0);
            studyActivityService.save(studyActivity);
        }
    }

    @Nested
    class updateTest{
        // TODO: 기존에 입력되어있던 스터디의 작성자와 수정 작성자가 다를 경우 수정 실패(memberId가 다를때) > 로그인 아이디 가져올 수 있을때

        @Test
        @DisplayName("성공")
        @Transactional
        public void update_success() {
            studyActivity.setTitle("test222");
            studyActivityService.update(studyActivity);
        }

        @Test
        @DisplayName("실패 > 필수값 null 입력")
        @Transactional
        public void update_null() {
            studyActivity.setTitle(null);
            studyActivityService.update(studyActivity);
        }

        @Test
        @DisplayName("실패 > 필수값 공백 입력")
        @Transactional
        public void update_blank() {
            studyActivity.setTitle("");
            studyActivityService.update(studyActivity);
        }

        @Test
        @DisplayName("실패 > 기존 입력된 StudyId와 수정된 StudyId가 다를때")
        @Transactional
        public void update_StudyIdIsDifferent() {
            _studyActivityId = studyActivityService.save(studyActivity);
            Optional<StudyActivity> _studyActivity = studyActivityService.findById(_studyActivityId);
            if(_studyActivity.isPresent()) {
                _studyActivity.get().setStudyId(0);
                studyActivityService.update(studyActivity);
            }
        }
    }

    @Nested
    class deleteTest {
        //TODO: 해당 스터디에 연결된 studyParticipant가 있을 경우 삭제 불가
        //TODO: 입력자와 다른 사용자가 삭제 시도시 삭제 불가 > 로그인 아이디 가져올 수 있을때

        @BeforeEach
        public void saveStudyActivity() {
            _studyActivityId = studyActivityService.save(studyActivity);
        }

        @AfterEach
        public void deleteStudy() {
            studyActivityService.delete(_studyActivityId);
        }

        @Test
        @DisplayName("성공")
        @Transactional
        public void delete_success() {
            System.out.println(_studyActivityId);
            if(studyActivityService.delete(_studyActivityId) > 0){
                System.out.println("삭제 성공");
            };
        }
    }
}
