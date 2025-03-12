package knou.seoul.hanwoori.domain.file;

import knou.seoul.hanwoori.domain.member.MemberService;
import knou.seoul.hanwoori.domain.member.dto.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FileServiceTest {

    @Autowired
    FileService fileService;

    @Autowired
    MemberService memberService;

    File file;
    Member member;
    @Autowired
    private FileServiceImpl fileServiceImpl;

    @BeforeEach
    public void setUpFile() {

        member = new Member();
        member.setLoginId("id");
        member.setPassword("pwd");
        member.setGrade(Member.Grade.basic);
        member.setName("주영");
        member.setGender(Member.Gender.male);
        memberService.save(member);

        file = new File();
        file.setSourceKind(File.SourceKind.member);
        file.setSourceId(member.getMemberId());
        file.setFilePath("파일경로");
        file.setOriginalFileName("오리지날파일명");
        file.setStoreFileName("저장된파일명");
    }

    @Test
    @DisplayName("파일 저장")
    @Rollback()
    public void save() {
        //Given

        //When
        fileService.save(file);

        //then
        assertThat(file.getFileId()).isNotNull();

    }

    @Test
    @DisplayName("전체 조회")
    @Rollback()
    public void findAll() {

        //Given
        fileService.save(file);
        fileService.save(file);

        //When
        List<File> files = fileService.findAll();

        //Then
        assertThat(files).hasSize(2);
    }

    @Test
    @DisplayName("ID로 조회")
    @Rollback()
    public void findById() {

        //Given
        fileService.save(file);

        //When
        Optional<File> foundFile = fileService.findById(file.getFileId());

        //Then
        assertThat(foundFile.get().getFileId()).isEqualTo(file.getFileId());
    }

    @Test
    @DisplayName("삭제")
    @Rollback()
    public void delete() {

        //Given
        fileService.save(file);

        //When
        fileService.delete(file.getFileId());
        Optional<File> foundFile = fileService.findById(file.getFileId());

        //Then
        assertThat(foundFile).isEmpty();

    }
}
