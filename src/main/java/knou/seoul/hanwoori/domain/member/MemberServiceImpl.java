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
        memberDAO.save(member);
    }

    @Override
    public List<Member> findAll() {
        return memberDAO.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberDAO.findById(id);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return memberDAO.findByName(name);
    }

    @Override
    public void modify(Member member) {
        memberDAO.modify(member);
    }
}
