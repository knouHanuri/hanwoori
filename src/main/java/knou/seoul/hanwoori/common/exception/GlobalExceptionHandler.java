package knou.seoul.hanwoori.common.exception;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/403"; // Thymeleaf 템플릿
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/500"; // Thymeleaf 템플릿
    }
}
