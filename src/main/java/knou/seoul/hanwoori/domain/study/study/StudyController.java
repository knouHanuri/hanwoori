package knou.seoul.hanwoori.domain.study.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    /*@GetMapping("/study/list")
    public String studyList(Model model){
        List<Study> studyList = studyService.studyListAll();
        model.addAttribute("status", EStudyStatus.values());
        model.addAttribute("studyList",studyList);
        return "study/studyList";
    }

    @GetMapping("/study/form")
    public String studyForm(@RequestParam(required = false) Long studyId, Model model){
        Optional<Study> optionalStudy = Optional.empty();
        if(studyId != null) optionalStudy = studyService.findById(studyId);
        model.addAttribute("status", EStudyStatus.values());
        model.addAttribute("study", optionalStudy.orElseGet(Study::new));
        return "study/studyForm";
    }

    @PostMapping("/study/formSave")
    public String create(@ModelAttribute @Valid Study studyForm, RedirectAttributes redirectAttributes, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시, 다시 폼으로 돌아감
            return "/study/form";
        }

        //study.setEstablishedBy("현재 사용자"); //나중에 현재 로그인 한 사용자로 저장

        String msg = "등록";
        if(studyForm.getStudyId() != null) {
            studyService.update(studyForm);
            msg = "수정";
        }
        else studyService.save(studyForm);

        redirectAttributes.addFlashAttribute("message", String.format("스터디가 %s되었습니다.", msg));
        return "redirect:/study/list";
    }

    @DeleteMapping("/study/delete/{studyId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long studyId) {
        int deleteCount = studyService.delete(studyId);
        if(deleteCount == 1) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/study/details")
    public String studyDetails(@RequestParam Long studyId, Model model) {
        // studyId로 특정 스터디 조회
        Optional<Study> optionalStudy = studyService.findById(studyId);

        if (optionalStudy.isPresent()) {
            // 조회한 Study 정보를 모델에 추가
            model.addAttribute("study", optionalStudy.get());
            return "study/studyDetails"; // Thymeleaf 템플릿 반환
        } else {
            // studyId가 잘못되었을 경우
            model.addAttribute("error", "해당 스터디를 찾을 수 없습니다.");
            return "study/studyDetails"; // 에러 메시지만 포함된 템플릿 반환
        }
    }*/
}
