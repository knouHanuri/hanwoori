package knou.seoul.hanwoori.domain.signup;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupListDTO;

import java.util.List;
import java.util.Optional;

public interface SignupService {
    void save(Signup signup);
    List<SignupListDTO> findAll();
    Optional<Signup> findById(Long id);
    void modify(Signup signup);
    int delete(Long id);
    List<Signup> findByMemberIdAndYear(Signup signup);
    int deleteByMemberIdAndYear(Signup signup);
}
