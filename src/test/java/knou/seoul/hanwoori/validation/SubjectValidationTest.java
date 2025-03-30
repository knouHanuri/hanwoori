package knou.seoul.hanwoori.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class SubjectValidationTest {

    @Test
    @DisplayName("과목 bean 유효성 검사")
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Subject subject = new Subject();
        subject.setSubjectName(" ");    //공백
        subject.setGrade(0);
        subject.setSemester(3);

        Set<ConstraintViolation<Subject>> validations = validator.validate(subject);
        for(ConstraintViolation<Subject> violation : validations) {
            System.out.println("violation = " + violation);
            System.out.println("message = " + violation.getMessage());
        }

    }
}
