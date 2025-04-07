package knou.seoul.hanwoori.common.web;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.common.SessionConst;
import knou.seoul.hanwoori.domain.member.dto.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;

@Slf4j
@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return member != null && member.getGrade() == Member.Grade.admin;
    }

    @Value("${file.upload.dir}")
    private String fileDir;

    @Value("${file.upload.path}")
    private String filePath;

    @ModelAttribute("fileDir")
    public String getFileDir(){
        return fileDir;
    }

    @ModelAttribute("filePath")
    public String getFilePath(){
        return filePath;
    }

    @PostConstruct
    public void init() {
        log.info("파일업로드 경로확인 {}",fileDir);
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 없으면 생성
        }
    }


}
