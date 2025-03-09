package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.domain.member.dto.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원 저장 및 이름으로 조회")
    @Rollback(true)
    public void save()
    {
        Member member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(0);
        member.setName("안녕");
        memberService.save(member);

        Optional<Member> foundMember = memberService.findByName(member.getName());
        Assertions.assertThat(foundMember.get().getName()).isEqualTo(member.getName());

    }
}
