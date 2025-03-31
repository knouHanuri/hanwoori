package knou.seoul.hanwoori.domain.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signups")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

}
