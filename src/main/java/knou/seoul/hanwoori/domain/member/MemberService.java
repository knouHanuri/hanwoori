package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.domain.member.dto.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    void modify(Member member);
}
