package knou.seoul.hanwoori.domain.auth;

import knou.seoul.hanwoori.domain.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AuthDAO {
    Optional<Member> findByLoginId(LoginRequestDTO loginRequestDTO);
}
