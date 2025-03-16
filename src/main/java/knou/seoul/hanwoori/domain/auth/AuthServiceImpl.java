package knou.seoul.hanwoori.domain.auth;

import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthDAO authDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginRequestDTO login(LoginRequestDTO loginRequestDTO) {
       Optional<Member> member = authDAO.findByLoginId(loginRequestDTO);
       member.ifPresent(m-> {
           passwordEncoder.matches(loginRequestDTO.getPassword(), m.getPassword());
       });
        return null;
    }
}
