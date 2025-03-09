package knou.seoul.hanwoori.domain.member.dao;

import knou.seoul.hanwoori.domain.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberDAO {
    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    void modify(Member member);

}
