package knou.seoul.hanwoori.domain.signup;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupFormRequestDTO;

import java.util.List;
import java.util.Optional;

public interface SignupService {
    void save(Signup signup);
    List<Signup> findAll();
    Optional<Signup> findById(Long id);
    int delete(Long id);
    List<Signup> findByMemberIdGroupBy(Long memberId);
    int deleteByMemberIdAndYear(Long memberId, Integer year);
    List<Signup> findByMemberIdAndYear(Long memberId, Integer year);
}
