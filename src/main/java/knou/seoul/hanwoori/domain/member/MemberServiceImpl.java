package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.common.util.AESUtil;
import knou.seoul.hanwoori.domain.member.dao.MemberDAO;
import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(Member member) {

        //암호화처리 : 이메일,전화번호,학번
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEmail(AESUtil.encrypt(member.getEmail()));
        member.setPhoneNumber(AESUtil.encrypt(member.getPhoneNumber()));
        member.setStudentNo(AESUtil.encrypt(member.getStudentNo()));

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
