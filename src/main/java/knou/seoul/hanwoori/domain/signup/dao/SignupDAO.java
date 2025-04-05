package knou.seoul.hanwoori.domain.signup.dao;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupFormRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SignupDAO {
    void save(SignupFormRequestDTO signupFormRequestDTO);
    List<Signup> findAll();
    Optional<Signup> findById(Long id);
    void modify(SignupFormRequestDTO signupFormRequestDTO);
    int delete(Long id);
    List<Signup> findByMemberIdAndYear(Signup signup);
    int deleteByMemberIdAndYear(Signup signup);
}
