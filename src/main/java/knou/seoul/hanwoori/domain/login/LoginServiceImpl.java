package knou.seoul.hanwoori.domain.login;

import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginDAO loginDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginRequestDTO login(LoginRequestDTO loginRequestDTO) {
       Optional<Member> member = loginDAO.findByLoginId(loginRequestDTO);
       member.ifPresent(m-> {
           passwordEncoder.matches(loginRequestDTO.getPassword(), m.getPassword());
       });
        return null;
    }
}
