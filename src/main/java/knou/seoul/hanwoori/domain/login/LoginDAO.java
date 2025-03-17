package knou.seoul.hanwoori.domain.login;

import knou.seoul.hanwoori.domain.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface LoginDAO {
    Optional<Member> findByLoginId(LoginRequestDTO loginRequestDTO);
}
