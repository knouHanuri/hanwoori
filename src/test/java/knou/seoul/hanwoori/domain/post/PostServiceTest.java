package knou.seoul.hanwoori.domain.post;

import knou.seoul.hanwoori.domain.file.File;
import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.post.dto.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    Post post;
    Member newMember;

    @BeforeEach
    public void setUpPost() {
        newMember = new Member();
        newMember.setLoginId("id");
        newMember.setPassword("pwd");
        newMember.setGrade(Member.Grade.basic);
        newMember.setName("주영");
        newMember.setGender(Member.Gender.male);
        memberService.save(newMember);

        post = new Post();
        post.setTitle("게시글제목");
        post.setMemberId(newMember.getMemberId());
        post.setCategory(Post.Category.free);
        post.setContent("##내용");

    }

    @Test
    @DisplayName("게시글 저장")
    @Rollback()
    public void save() {
        //Given

        //When
        postService.save(post);

        //then
        assertThat(post.getPostId()).isNotNull();
    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        postService.save(post);
        postService.save(post);

        //When
        List<Post> posts = postService.findAll();

        //Then
        assertThat(posts).hasSize(2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById() {

        //Given
        postService.save(post);

        //When
        Optional<Post> foundPost = postService.findById(post.getPostId());

        //Then
        assertThat(foundPost.get().getPostId()).isEqualTo(post.getPostId());
    }

    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify() {

        //Given
        postService.save(post);
        Optional<Post> foundPost = postService.findById(post.getPostId());
        foundPost.get().setCategory(Post.Category.qna);
        foundPost.get().setContent("###수정된내용");

        //변경확인용 Map
        Map<Function<Post, Object>, Object> updates = Map.of(
                Post::getCategory, foundPost.get().getCategory(),
                Post::getContent, foundPost.get().getContent()
        );

        //When
        postService.modify(foundPost.get());

        //Then
        Optional<Post> modifiedPost = postService.findById(foundPost.get().getPostId());
        updates.forEach((getter,expectedValue) ->
                assertThat(expectedValue).isEqualTo(getter.apply(modifiedPost.get()))
        );
    }

    @Test
    @DisplayName("삭제")
    @Rollback()
    public void delete() {

        //Given
        postService.save(post);

        //When
        postService.delete(post.getPostId());
        Optional<Post> foundPost = postService.findById(post.getPostId());

        //Then
        assertThat(foundPost).isEmpty();

    }


}
