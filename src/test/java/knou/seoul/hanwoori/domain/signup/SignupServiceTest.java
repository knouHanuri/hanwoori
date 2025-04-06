package knou.seoul.hanwoori.domain.signup;

import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupFormRequestDTO;
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
        signup.setMember(member);
        signup.setSubject(subject);
        signup.setYear(2025);

    }

    @Test
    @DisplayName("수강신청 저장")
    @Rollback()
    public void save() {
        //Given

        //When
        SignupFormRequestDTO signupFormRequestDTO = Signup.form(signup);
        //signupService.save(signupFormRequestDTO);

        //then
        assertThat(signupFormRequestDTO.getSignupId()).isNotNull();

    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        int count = signupService.findAll().size();
        sqlSession.clearCache();

        //signupService.save(Signup.form(signup));
        //signupService.save(Signup.form(signup));

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
        SignupFormRequestDTO signupFormRequestDTO = Signup.form(signup);
        //signupService.save(signupFormRequestDTO);

        //When
        Optional<Signup> findSignup = signupService.findById(signupFormRequestDTO.getSignupId());

        //Then
        assertThat(findSignup.get().getSignupId()).isEqualTo(signupFormRequestDTO.getSignupId());
    }

    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify() {

        //Given
        SignupFormRequestDTO signupFormRequestDTO = Signup.form(signup);
        //signupService.save(signupFormRequestDTO);
        Optional<Signup> findSignup = signupService.findById(signupFormRequestDTO.getSignupId());

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

        findSignup.get().setMember(member);
        findSignup.get().setSubject(subject);
        findSignup.get().setYear(2024);

        //변경확인용 Map
        Map<Function<Signup, Object>, Object> updates = Map.of(
                Signup::getYear, findSignup.get().getYear()
        );

        //When
        SignupFormRequestDTO findSignupFormRequestDTO = Signup.form(findSignup.get());
        //signupService.modify(findSignupFormRequestDTO);

        //Then
        Optional<Signup> modifiedSignup = signupService.findById(findSignupFormRequestDTO.getSignupId());
        updates.forEach((getter,expectedValue) ->
                assertThat(expectedValue).isEqualTo(getter.apply(modifiedSignup.get()))
        );
    }

    @Test
    @DisplayName("삭제")
    @Rollback()
    public void delete() {

        //Given
        //signupService.save(Signup.form(signup));

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
        SignupFormRequestDTO signupFormRequestDTO = Signup.form(signup);
        //signupService.save(signupFormRequestDTO);
        Optional<Signup> findSignup = signupService.findById(signupFormRequestDTO.getSignupId());

        //When
        //List<Signup> signups = signupService.findByMemberIdAndYear(findSignup.orElse(new Signup()));

        //Then
        //assertThat(signups).hasSize(1);
    }

    @Test
    @DisplayName("회원ID,연도로 삭제")
    @Rollback()
    public void deleteByMemberIdAndYear() {

        //Given
        SignupFormRequestDTO signupFormRequestDTO = Signup.form(signup);
        //signupService.save(signupFormRequestDTO);
        Optional<Signup> findSignup = signupService.findById(signupFormRequestDTO.getSignupId());

        //When
        //signupService.deleteByMemberIdAndYear(findSignup.orElse(new Signup()));
        Optional<Signup> findSignup2 = signupService.findById(findSignup.orElse(new Signup()).getSignupId());

        //Then
        assertThat(findSignup2).isEmpty();

    }
}
