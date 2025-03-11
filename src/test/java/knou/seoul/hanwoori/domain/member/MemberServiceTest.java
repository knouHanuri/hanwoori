package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.member.dto.MemberRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    Member member;

    @BeforeEach
    public void setUpMember() {
        member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("주영");
        member.setEmail("email@email.com");
        member.setPhoneNumber("1234567890");
        member.setGender(Member.Gender.male);
        member.setBirthdate(LocalDate.of(1988, 11, 12));
        member.setStudentNo("studentNo");
        member.setRemark("비고");
    }

    @Test
    @DisplayName("회원 저장")
    @Rollback()
    public void join() {
        //Given

        //When
        memberService.save(member);

        //then
        assertThat(member.getMemberId()).isNotNull();
        //pwd 체크
        Optional<Member> foundMember = memberService.findById(member.getMemberId());
        assertThat(member.getPassword()).isEqualTo(foundMember.get().getPassword());

    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        memberService.save(member);
        memberService.save(member);

        //When
        List<Member> members = memberService.findAll();

        //Then
        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById() {

        //Given
        memberService.save(member);

        //When
        Optional<Member> foundMember = memberService.findById(member.getMemberId());

        //Then
        assertThat(foundMember.get().getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify() {

        //Given
        memberService.save(member);
        Optional<Member> foundMember = memberService.findById(member.getMemberId());
        foundMember.get().setName("수정된이름");
        foundMember.get().setGrade(Member.Grade.admin);
        foundMember.get().setEmail("email@gmail.com");
        foundMember.get().setPhoneNumber("01010002000");
        foundMember.get().setGender(Member.Gender.female);
        foundMember.get().setBirthdate(LocalDate.of(1988, 1, 2));
        foundMember.get().setStudentNo("studentNo2");
        foundMember.get().setRemark("수정된비고");

        //변경확인용 Map
        Map<Function<Member, Object>, Object> updates = Map.of(
                Member::getName, foundMember.get().getName(),
                Member::getGrade, foundMember.get().getGrade(),
                Member::getEmail, foundMember.get().getEmail(),
                Member::getPhoneNumber, foundMember.get().getPhoneNumber(),
                Member::getGender, foundMember.get().getGender(),
                Member::getBirthdate, foundMember.get().getBirthdate(),
                Member::getStudentNo, foundMember.get().getStudentNo(),
                Member::getRemark, foundMember.get().getRemark()
        );

        //When
        memberService.modify(foundMember.get());

        //Then
        Optional<Member> modifiedMember = memberService.findById(foundMember.get().getMemberId());
        updates.forEach((getter,expectedValue) ->
                    assertThat(expectedValue).isEqualTo(getter.apply(modifiedMember.get()))
                );
    }
}
