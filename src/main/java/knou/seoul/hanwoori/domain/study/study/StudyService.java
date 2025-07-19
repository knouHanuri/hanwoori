package knou.seoul.hanwoori.domain.study.study;

import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import knou.seoul.hanwoori.domain.study.study.dto.Study;
import knou.seoul.hanwoori.domain.study.study.dto.StudySearchRequestDTO;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface StudyService {
    long save(@Valid Study study);
    void update(@Valid Study study);
    Optional<Study> findById(long studyId);
    PageInfo<Study> studyListAll(int pageNum, int pageSize);
    PageInfo<Study> studyListAll(int pageNum, int pageSize, @Nullable StudySearchRequestDTO searchRequest);
    List<Study> studyListLimited(int limit);
    int delete(long studyId);
}
