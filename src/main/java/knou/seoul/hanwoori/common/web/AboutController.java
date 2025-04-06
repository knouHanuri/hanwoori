package knou.seoul.hanwoori.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping("/findus")
    public String findus(){
        return "about/findus";
    }

    @GetMapping("/contact")
    public String contact(){
        return "about/contact";
    }

}
