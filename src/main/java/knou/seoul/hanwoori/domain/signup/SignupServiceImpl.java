package knou.seoul.hanwoori.domain.signup;

import knou.seoul.hanwoori.domain.signup.dao.SignupDAO;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.signup.dto.SignupListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final SignupDAO signupDAO;

    @Override
    public void save(Signup signup) {
        signupDAO.save(signup);
    }

    @Override
    public List<SignupListDTO> findAll() {
        return signupDAO.findAll();
    }

    @Override
    public Optional<Signup> findById(Long id) {
        return signupDAO.findById(id);
    }

    @Override
    public void modify(Signup signup) {
        signupDAO.modify(signup);
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
