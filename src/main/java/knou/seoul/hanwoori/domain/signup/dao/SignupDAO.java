package knou.seoul.hanwoori.domain.signup.dao;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupFormRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SignupDAO {
    void save(Signup signup);
    List<Signup> findAll();
    Optional<Signup> findById(Long id);
    int delete(Long id);
    List<Signup> findByMemberIdGroupBy(Long memberId);
    int deleteByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") Integer year);
    List<Signup> findByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") Integer year);
}
