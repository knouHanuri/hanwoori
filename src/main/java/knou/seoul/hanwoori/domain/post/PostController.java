package knou.seoul.hanwoori.domain.post;

import jakarta.servlet.http.HttpSession;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.post.dto.Post;
import knou.seoul.hanwoori.domain.signup.dto.Signup;
import knou.seoul.hanwoori.domain.subject.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static knou.seoul.hanwoori.common.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @ModelAttribute("categorys")
    public Post.Category[] categorys() {
        return Post.Category.values();
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new Post());
        return "domain/post/post-form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute Post post, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "domain/post/post-form";
        }

        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));
        post.setMember(loginMember.orElseGet(Member::new));
        postService.save(post);
        Long postId = post.getPostId();

        return "redirect:/posts/" + postId;
    }

    @GetMapping
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "domain/post/post-list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.findById(id);
        model.addAttribute("post", post.orElseGet(Post::new));
        return "domain/post/post-view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.findById(id);
        model.addAttribute("post", post.orElseGet(Post::new));
        return "domain/post/post-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String modify(@PathVariable Long id, @Validated @ModelAttribute Post post,BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()) {
            return "domain/post/post-edit-form";
        }
        Optional<Member> loginMember = Optional.ofNullable((Member) session.getAttribute(LOGIN_MEMBER));
        post.setMember(loginMember.orElseGet(Member::new));
        postService.modify(post);
        return "redirect:/posts/" + id;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int count = postService.delete(id);
        if (count == 1) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
