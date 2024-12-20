package com.example.androidserver.Question.service;

import com.example.androidserver.Question.model.Question;
import com.example.androidserver.Question.repo.QuestionRepo;
import com.example.androidserver.infrastructure.utils.JWTUtils;
import com.example.androidserver.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final UserRepo userRepo;
    private final JWTUtils utils;

    /**
     * 새로운 질문을 저장하는 메서드
     *
     * @param question 저장할 질문 객체
     * @return 저장된 질문의 결과 값
     */
    public boolean createServiceQuestionService(Question question, String token) {
        try {
            String email = utils.getEmail(token);           // 토큰에서 이메일 추출
            Integer uid = userRepo.findUidByEmailRepo(email);   // 이메일로 UID 조회
            if (uid == null) {
                log.error("UID not found for email: {}", email);
                return false;  // UID를 찾지 못한 경우 실패 처리
            }
            question.setUid(uid);                                 // UID를 Question 객체에 설정
            return questionRepo.createRepoQuestionRepo(question);     // 질문 저장 로직 호출
        } catch (Exception e) {
            log.error("Error occurred in saveQuestion: {}", e.getMessage(), e);
            return false;  // 예외 발생 시 false 반환
        }
    }

    /**
     * 카테고리별 질문 목록을 가져오는 메서드
     * @param category 조회할 카테고리
     * @return 카테고리에 해당하는 질문 목록
     */
    public List<Question> getCategoryQuestionsService(String category) {
        return questionRepo.selectQuestionByCategoryRepo(category); // 카테고리별 질문 조회
    }

    public List<Question> getMyQuestionService(int uid) {
        return questionRepo.selectMyQuestionRepo(uid);
    }

    /**
     * 카테고리와 제목을 기반으로 질문을 검색하는 메서드
     * @param keyword 사용자가 입력한 데이터
     * @return 카테고리와 제목에 맞는 질문 목록
     */
    public List<Question> searchQuestionsByKeywordService(String keyword) {
        return questionRepo.selectQuestionByKeywordRepo(keyword); // 카테고리와 제목을 기반으로 질문 검색
    }

    /**
     * 질문을 수정하는 메서드
     * @param question 수정할 질문 객체
     * @return 수정 결과 값 (성공 시 1, 실패 시 0)
     */
    public int updateQuestionService(Question question) {
        return questionRepo.updateQuestionRepo(question); // 질문 수정 로직 호출
    }

    /**
     * 질문을 삭제하는 메서드
     * @param qid 삭제할 질문의 ID
     * @return 삭제 결과 값 (성공 시 1, 실패 시 0)
     */
    public int deleteQuestionService(int qid) {
        return questionRepo.deleteQuestionRepo(qid); // 질문 삭제 로직 호출
    }

    // 좋아요
    public int greatQuestionService(int qid) {
        questionRepo.incrementGreatRepo(qid);
        return questionRepo.getGreatCountRepo(qid);
    }
}
