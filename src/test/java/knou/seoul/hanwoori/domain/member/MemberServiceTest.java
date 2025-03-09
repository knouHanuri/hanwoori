package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.member.dto.MemberRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원 저장")
    @Rollback()
    public void join()
    {
        //Given
        Member member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("안녕");

        //When
        memberService.save(member);

        //then
        assertThat(member.getMemberId()).isNotNull();

    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll(){

        //Given
        Member member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("안녕");

        //When
        memberService.save(member);
        memberService.save(member);
        List<Member> members = memberService.findAll();

        //Then
        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById(){

        //Given
        Member member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("안녕");

        //When
        memberService.save(member);
        Optional<Member> foundMember = memberService.findById(member.getMemberId());

        //Then
        assertThat(foundMember.get().getMemberId()).isEqualTo(member.getMemberId());
    }
    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify(){

        //Given
        Member member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("안녕");

        //When
        memberService.save(member);
        Optional<Member> foundMember = memberService.findById(member.getMemberId());
        foundMember.get().setGrade(Member.Grade.admin);
        memberService.modify(foundMember.get());

        //Then
        Optional<Member>  modifiedMember = memberService.findById(foundMember.get().getMemberId());
        assertThat(modifiedMember.get().getGender()).isEqualTo(foundMember.get().getGender());
    }
}
