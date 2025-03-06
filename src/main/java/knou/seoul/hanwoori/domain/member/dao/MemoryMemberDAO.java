package knou.seoul.hanwoori.domain.member.dao;

import knou.seoul.hanwoori.domain.member.dto.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemoryMemberDAO implements MemberDAO {

    @Override
    public void save(Member member) {

    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Optional<Member> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Member member) {

    }
}

