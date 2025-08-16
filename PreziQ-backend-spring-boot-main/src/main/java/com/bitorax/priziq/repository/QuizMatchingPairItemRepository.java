package com.bitorax.priziq.repository;

import com.bitorax.priziq.domain.activity.quiz.QuizMatchingPairAnswer;
import com.bitorax.priziq.domain.activity.quiz.QuizMatchingPairItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizMatchingPairItemRepository extends JpaRepository<QuizMatchingPairItem, String>, JpaSpecificationExecutor<QuizMatchingPairItem> {
    @Query("SELECT COALESCE(MAX(i.displayOrder), 0) FROM QuizMatchingPairItem i WHERE i.quizMatchingPairAnswer = :answer AND i.isLeftColumn = :isLeftColumn")
    Optional<Integer> findMaxDisplayOrderByQuizMatchingPairAnswerAndIsLeftColumn(QuizMatchingPairAnswer answer, Boolean isLeftColumn);

    @Modifying
    @Query("UPDATE QuizMatchingPairItem i SET i.displayOrder = i.displayOrder + 1 WHERE i.quizMatchingPairAnswer = :answer AND i.isLeftColumn = :isLeftColumn AND i.displayOrder >= :displayOrder")
    void incrementDisplayOrder(QuizMatchingPairAnswer answer, Boolean isLeftColumn, Integer displayOrder);

    @Modifying
    @Query("UPDATE QuizMatchingPairItem i SET i.displayOrder = i.displayOrder - 1 WHERE i.quizMatchingPairAnswer = :answer AND i.isLeftColumn = :isLeftColumn AND i.displayOrder > :displayOrder")
    void decrementDisplayOrder(QuizMatchingPairAnswer answer, Boolean isLeftColumn, Integer displayOrder);

    @Query("SELECT i FROM QuizMatchingPairItem i WHERE i.quizMatchingPairAnswer = :answer AND i.isLeftColumn = :isLeftColumn ORDER BY i.displayOrder ASC")
    List<QuizMatchingPairItem> findByQuizMatchingPairAnswerAndIsLeftColumnOrderByDisplayOrderAsc(
            @Param("answer") QuizMatchingPairAnswer answer,
            @Param("isLeftColumn") Boolean isLeftColumn);

    @Query("SELECT i FROM QuizMatchingPairItem i WHERE i.quizMatchingPairAnswer = :answer AND i.isLeftColumn = :isLeftColumn AND i.displayOrder = :displayOrder")
    Optional<QuizMatchingPairItem> findByQuizMatchingPairAnswerAndIsLeftColumnAndDisplayOrder(
            @Param("answer") QuizMatchingPairAnswer answer,
            @Param("isLeftColumn") Boolean isLeftColumn,
            @Param("displayOrder") Integer displayOrder);
}