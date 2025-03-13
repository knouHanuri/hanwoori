package knou.seoul.hanwoori.domain.member;

import knou.seoul.hanwoori.common.util.AESUtil;
import knou.seoul.hanwoori.domain.member.dao.MemberDAO;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.member.dto.MemberPasswordRequestDTO;
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

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        aesEncrypt(member);
        memberDAO.save(member);
    }

    @Override
    public List<Member> findAll() {

        List<Member> members = memberDAO.findAll();

        for (Member member : members) {
            aesDecrypt(member);
        }
        return members;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Optional<Member> member = memberDAO.findById(id);
        member.ifPresent(this::aesDecrypt);
        return member;
    }

    @Override
    public Optional<Member> findByName(String name) {
        Optional<Member> member = memberDAO.findByName(name);
        member.ifPresent(this::aesDecrypt);
        return member;
    }

    @Override
    public void modify(Member member) {
        aesEncrypt(member);
        memberDAO.modify(member);
    }

    @Override
    public void delete(Long id) {
        memberDAO.delete(id);
    }

    @Override
    public void modifyPassword(MemberPasswordRequestDTO requestDTO) {

        memberDAO.findById(requestDTO.getMemberId()).ifPresent(member -> {
            if (member.getPassword().equals(passwordEncoder.encode(requestDTO.getOldPassword()))) {
                member.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
                memberDAO.modifyPassword(member);
            }

        });
    }

    // region : 개인정보 암복호화
    private void aesEncrypt(Member member) {
        member.setEmail(AESUtil.encrypt(member.getEmail()));
        member.setPhoneNumber(AESUtil.encrypt(member.getPhoneNumber()));
        member.setStudentNo(AESUtil.encrypt(member.getStudentNo()));
    }

    private void aesDecrypt(Member member) {
        member.setEmail(AESUtil.decrypt(member.getEmail()));
        member.setPhoneNumber(AESUtil.decrypt(member.getPhoneNumber()));
        member.setStudentNo(AESUtil.decrypt(member.getStudentNo()));
    }
    // endregion

}


