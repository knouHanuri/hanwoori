package knou.seoul.hanwoori.domain.signup;

import knou.seoul.hanwoori.domain.signup.dao.SignupDAO;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupFormRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final SignupDAO signupDAO;

    @Override
    public void save(SignupFormRequestDTO signupFormRequestDTO) {
        signupDAO.save(signupFormRequestDTO);
    }

    @Override
    public List<Signup> findAll() {
        return signupDAO.findAll();
    }

    @Override
    public Optional<Signup> findById(Long id) {
        return signupDAO.findById(id);
    }

    @Override
    public void modify(SignupFormRequestDTO signupFormRequestDTO) {
        signupDAO.modify(signupFormRequestDTO);
    }

    @Override
    public int delete(Long id) {
        return signupDAO.delete(id);
    }

    @Override
    public List<Signup> findByMemberIdAndYear(Signup signup) {
        return signupDAO.findByMemberIdAndYear(signup);
    }

    @Override
    public int deleteByMemberIdAndYear(Signup signup) {
        return signupDAO.deleteByMemberIdAndYear(signup);
    }
}
