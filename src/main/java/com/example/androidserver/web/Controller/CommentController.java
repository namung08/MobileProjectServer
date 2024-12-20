package com.example.androidserver.web.Controller;

import com.example.androidserver.Comment.model.Comment;
import com.example.androidserver.Comment.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    // 댓글 조회
    @GetMapping("/get/{qid}")
    public List<Comment> getComment(@PathVariable("qid") int qid) {
        return commentService.selectCommentByQuestionId(qid);
    }

    // 댓글 등록
    @PostMapping("/post/create")
    public String createComment(
            @RequestHeader("Authorization") String token,
            @RequestBody Comment comment) {
        log.info("post/create" + comment + " , " + token);
        int result = commentService.createComment(comment, token);
        return result == 1 ? "답변이 등록되었습니다." : "답변이 등록되지 않았습니다.";
    }

    // 댓글 수정
    @PostMapping("/post/update")
    public String updateComment(@RequestBody Comment comment) {
        int result = commentService.updateComment(comment);
        return result == 1 ? "답변이 수정되었습니다." : "답변이 등록되지 않았습니다.";
    }

    // 댓글 삭제
    @PostMapping("/post/delete/{cid}")
    public String deleteComment(@PathVariable int cid) {
        int result = commentService.deleteComment(cid);
        return result == 1 ? "답변이 삭제되었습니다." : "답변 삭제에 실패하였습니다.";
    }

    // 게시글 내 존재하는 모든 댓글 삭제
    @PostMapping("/post/question-all/delite/{qid}")
    public String deleteAllComment(@PathVariable int qid) {
        int result = commentService.deleteAllComment(qid);
        return result == 1 ? "답변이 삭제되었습니다." : "답변 삭제에 실패하였습니다.";
    }
}
