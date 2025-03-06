package knou.seoul.hanwoori.domain.member.dao;

import knou.seoul.hanwoori.domain.member.dto.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDAO {
    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(int id);
    void update(Member member);
}
