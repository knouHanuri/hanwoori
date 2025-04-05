package knou.seoul.hanwoori.domain.login;

import knou.seoul.hanwoori.domain.member.dao.MemberDAO;
import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberDAO memberDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(LoginRequestDTO loginRequestDTO) {
        return memberDAO.findByLoginId(loginRequestDTO.getLoginId())
                .filter(m -> passwordEncoder.matches(loginRequestDTO.getPassword(),m.getPassword()))
                .orElse(null);
    }
}
