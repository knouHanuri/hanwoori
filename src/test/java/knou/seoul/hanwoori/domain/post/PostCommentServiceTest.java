package knou.seoul.hanwoori.domain.post;

import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import knou.seoul.hanwoori.domain.post.comment.PostCommentService;
import knou.seoul.hanwoori.domain.post.comment.dto.PostComment;
import knou.seoul.hanwoori.domain.post.dto.Post;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostCommentServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    @Autowired
    PostCommentService postCommentService;

    @Autowired
    SqlSession sqlSession;


    Post post;
    Member member;
    PostComment postComment;


    @BeforeEach
    public void setUpPostComment() {
        member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("주영");
        member.setGender(Member.Gender.male);
        memberService.save(member);

        post = new Post();
        post.setTitle("게시글제목");
        post.setMemberId(member.getMemberId());
        post.setCategory(Post.Category.free);
        post.setContent("##내용");
        postService.save(post);

        postComment = new PostComment();
        postComment.setPostId(post.getPostId());
        postComment.setMemberId(post.getMemberId());
        postComment.setComment("댓글내용!!");

    }

    @Test
    @DisplayName("댓글 저장")
    @Rollback()
    public void save() {
        //Given

        //When
        postCommentService.save(postComment);

        //then
        assertThat(postComment.getPostId()).isNotNull();
    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        int count = postCommentService.findAll().size();
        sqlSession.clearCache();

        postCommentService.save(postComment);
        postCommentService.save(postComment);

        //When
        List<PostComment> postComments = postCommentService.findAll();

        //Then
        assertThat(postComments).hasSize(count + 2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById() {

        //Given
        postCommentService.save(postComment);

        //When
        Optional<PostComment> foundPostComment = postCommentService.findById(postComment.getPostCommentId());

        //Then
        assertThat(foundPostComment.get().getPostCommentId()).isEqualTo(postComment.getPostCommentId());
    }

    @Test
    @DisplayName("수정")
    @Rollback()
    public void modify() {

        //Given
        postCommentService.save(postComment);
        Optional<PostComment> foundPostComment = postCommentService.findById(postComment.getPostCommentId());
        foundPostComment.get().setComment("수정된댓글내용");
        foundPostComment.get().setUpdatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        //변경확인용 Map
        Map<Function<PostComment, Object>, Object> updates = Map.of(
                PostComment::getComment, foundPostComment.get().getComment(),
                PostComment::getUpdatedDate, foundPostComment.get().getUpdatedDate()
        );

        //When
        postCommentService.modify(foundPostComment.get());

        //Then
        Optional<PostComment> modifiedPostComment = postCommentService.findById(foundPostComment.get().getPostCommentId());
        updates.forEach((getter,expectedValue) ->
                assertThat(expectedValue).isEqualTo(getter.apply(modifiedPostComment.get()))
        );
    }

    @Test
    @DisplayName("삭제")
    @Rollback()
    public void delete() {

        //Given
        postCommentService.save(postComment);

        //When
        postCommentService.delete(postComment.getPostCommentId());
        Optional<PostComment> foundPostCommon = postCommentService.findById(postComment.getPostCommentId());

        //Then
        assertThat(foundPostCommon).isEmpty();

    }


}
