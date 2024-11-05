package com.example.androidserver.web.Controller;

import com.example.androidserver.Comment.service.CommentService;
import com.example.androidserver.Question.model.Question;
import com.example.androidserver.Question.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final CommentService commentService;

    // 질문 저장
    @PostMapping("/post/save")
    public String saveQuestion(
            @RequestHeader("Authorization") String token,
            @RequestBody Question question) {
        int result = questionService.saveQuestion(question, token);
        return result == 1 ? "successfully!" : "Failed";
    }

    // 질문 수정
    @PostMapping("/post/{qid}/update")
    public int updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    // 질문 삭제
    @PostMapping("/post/{qid}/delete")
    public int deleteQuestion(@PathVariable int qid) {
        commentService.deleteAllComment(qid);
        return questionService.deleteQuestion(qid);
    }

    // 카테고리가 질문하기인 데이터 출력
    @GetMapping("/get/category/question")
    public List<Question> getCategoryQuestion() {
        return questionService.getCategoryQuestions("질문답변");
    }

    // 카테고리가 자유게시판인 데이터 출력
    @GetMapping("/get/category/free-board")
    public List<Question> getCategoryFreeBoard() {
        return questionService.getCategoryQuestions("자유 게시판");
    }

    // 특정 사용자가 작성한 데이터 출력
    @GetMapping("/get/my-post/{uid}")
    public List<Question> getMyQuestion(@PathVariable int uid) {
        return questionService.getMyQuestion(uid);
    }

    // 좋아요 버튼 클릭
    @PostMapping("/post/{qid}/great")
    public int greatQuestion(@PathVariable int qid) {
        return 1;
    }
}
