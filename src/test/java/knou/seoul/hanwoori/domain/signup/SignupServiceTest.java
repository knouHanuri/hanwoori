package knou.seoul.hanwoori.domain.signup;

import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.subject.SubjectService;
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
public class SignupServiceTest {


    @Autowired
    SignupService signupService;

    @Autowired
    MemberService memberService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    private SqlSession sqlSession;

    Signup signup;
    Member member;
    Subject subject;

    @BeforeEach
    public void setUpSignup() {
        member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("주영");
        member.setGender(Member.Gender.male);
        memberService.save(member);

        subject = new Subject();
        subject.setSubjectName("컴이해");
        subject.setGrade(1);
        subject.setSemester(1);
        subject.setProfessor("누구지");
        subject.setRemark("비고");
        subjectService.save(subject);

        signup = new Signup();
        signup.setMemberId(member.getMemberId());
        signup.setSubjectId(subject.getSubjectId());
        signup.setYear(2025);

    }

    @Test
    @DisplayName("과목 저장")
    @Rollback()
    public void save() {
        //Given

        //When
        signupService.save(signup);

        //then
        assertThat(signup.getSignupId()).isNotNull();

    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        int count = signupService.findAll().size();
        sqlSession.clearCache();

        signupService.save(signup);
        signupService.save(signup);

        //When
        List<Signup> signups = signupService.findAll();

        //Then
        assertThat(signups).hasSize(count + 2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById() {

        //Given
        signupService.save(signup);

        //When
        Optional<Signup> findSignup = signupService.findById(signup.getSignupId());

        //Then
        assertThat(findSignup.get().getSignupId()).isEqualTo(signup.getSignupId());
    }

    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify() {

        //Given
        signupService.save(signup);
        Optional<Signup> findSignup = signupService.findById(signup.getSignupId());

        sqlSession.clearCache();

        member = new Member();
        member.setLoginId("newId");
        member.setPassword("newPwd");
        member.setGrade(Member.Grade.basic);
        member.setName("상현");
        member.setGender(Member.Gender.male);
        memberService.save(member);

        subject = new Subject();
        subject.setSubjectName("자료구조");
        subject.setGrade(3);
        subject.setSemester(2);
        subject.setProfessor("누구지");
        subject.setRemark("비고");
        subjectService.save(subject);

        findSignup.get().setMemberId(member.getMemberId());
        findSignup.get().setSubjectId(subject.getSubjectId());
        findSignup.get().setYear(2024);

        //변경확인용 Map
        Map<Function<Signup, Object>, Object> updates = Map.of(
                Signup::getMemberId, findSignup.get().getMemberId(),
                Signup::getSubjectId, findSignup.get().getSubjectId(),
                Signup::getYear, findSignup.get().getYear()
        );

        //When
        signupService.modify(findSignup.get());

        //Then
        Optional<Signup> modifiedSignup = signupService.findById(findSignup.get().getSignupId());
        updates.forEach((getter,expectedValue) ->
                assertThat(expectedValue).isEqualTo(getter.apply(modifiedSignup.get()))
        );
    }

    @Test
    @DisplayName("삭제")
    @Rollback()
    public void delete() {

        //Given
        signupService.save(signup);

        //When
        signupService.delete(signup.getSignupId());
        Optional<Signup> findSignup = signupService.findById(signup.getSignupId());

        //Then
        assertThat(findSignup).isEmpty();

    }

    @Test
    @DisplayName("회원ID,연도로 조회")
    @Rollback()
    public void findByMemberIdAndYear() {

        //Given
        signupService.save(signup);

        //When
        List<Signup> signups = signupService.findByMemberIdAndYear(signup);

        //Then
        assertThat(signups).hasSize(1);
    }

    @Test
    @DisplayName("회원ID,연도로 삭제")
    @Rollback()
    public void deleteByMemberIdAndYear() {

        //Given
        signupService.save(signup);

        //When
        signupService.deleteByMemberIdAndYear(signup);
        Optional<Signup> findSignup = signupService.findById(signup.getSignupId());

        //Then
        assertThat(findSignup).isEmpty();

    }
}
