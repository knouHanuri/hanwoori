package knou.seoul.hanwoori.domain.login;

import knou.seoul.hanwoori.domain.member.dto.Member;

public interface LoginService {
    Member login(LoginRequestDTO loginRequestDTO);

}
