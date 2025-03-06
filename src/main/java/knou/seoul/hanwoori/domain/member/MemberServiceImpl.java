package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.domain.member.dao.MemberDAO;
import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;

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
