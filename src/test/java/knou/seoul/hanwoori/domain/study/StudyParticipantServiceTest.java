package knou.seoul.hanwoori.domain.study;

import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.study.study.StudyService;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.studyParticipant.StudyParticipantService;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipantParam;
import knou.seoul.hanwoori.domain.study.studyParticipant.dto.StudyParticipant;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class StudyParticipantServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    StudyService studyService;
    @Autowired
    StudyParticipantService studyParticipantService;

    Member member;

    long _studyId = 0;
    Study study;

    long _studyParticipantId = 0;
    StudyParticipant studyParticipant;

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

        studyParticipant = new StudyParticipant();
        studyParticipant.setStudyId(_studyId);
        studyParticipant.setMemberId(1);
    }

    @AfterEach
    public void deleteStudy() {
        studyService.delete(_studyId);
    }

    @Nested
    class InputTest {
        //TODO: 사용자 비교 추가(아이디가 member에 있는지)
        //TODO: 이미 추가되어있는지 확인
        
        @Test
        @DisplayName("성공")
        public void save_success() {
            studyParticipantService.save(studyParticipant);
        }

        @Test
        @DisplayName("성공 > 상위 스터디 없음(로그에 실패)")
        public void save_studyNotExists() {
            studyParticipant.setStudyId(0);
            studyParticipantService.save(studyParticipant);
        }

        @Test
        @DisplayName("성공 > 이미 존재하는 참여자(로그에 실패)")
        public void save_userAlreadyExists() {
            studyParticipantService.save(studyParticipant);
            studyParticipantService.save(studyParticipant);
        }
    }

    @Nested
    class deleteTest {
        //TODO: 입력자와 다른 사용자가 삭제 시도시 삭제 불가 > 로그인 아이디 가져올 수 있을때

        @BeforeEach
        public void saveStudyActivity() {
            _studyParticipantId = studyParticipantService.save(studyParticipant);
        }

        @Test
        @DisplayName("성공")
        @Transactional
        public void delete_success() {
            System.out.println(_studyParticipantId);

            StudyParticipantParam param = studyParticipantService.createStudyParticipantParam(studyParticipant.getStudyId(), studyParticipant.getMemberId());

            if(studyParticipantService.delete(param) > 0){
                System.out.println("삭제 성공");
            };
        }
    }
}
