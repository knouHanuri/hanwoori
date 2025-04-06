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
    public void save(Signup signup) {
        signupDAO.save(signup);
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
    public int delete(Long id) {
        return signupDAO.delete(id);
    }

    @Override
    public List<Signup> findByMemberIdGroupBy(Long memberId) {
        return signupDAO.findByMemberIdGroupBy(memberId);
    }

    @Override
    public int deleteByMemberIdAndYear(Long memberId, Integer year) {
        return signupDAO.deleteByMemberIdAndYear(memberId,year);
    }

    @Override
    public List<Signup> findByMemberIdAndYear(Long memberId, Integer year) {
        return signupDAO.findByMemberIdAndYear(memberId,year);
    }
}
