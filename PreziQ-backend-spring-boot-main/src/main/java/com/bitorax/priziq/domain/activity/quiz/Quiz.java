package com.bitorax.priziq.domain.activity.quiz;

import com.bitorax.priziq.constant.PointType;
import com.bitorax.priziq.domain.BaseEntity;
import com.bitorax.priziq.domain.activity.Activity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "quizzes")
public class Quiz extends BaseEntity {
    @Id
    String quizId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "activity_id", nullable = false)
    @JsonIgnore
    Activity activity;

    @Column(columnDefinition = "TEXT", nullable = false)
    String questionText;

    @Column(nullable = false)
    @Builder.Default
    Integer timeLimitSeconds = 30;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PointType pointType;

    // Quiz buttons, checkboxes, true/false, reorder, type answer
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizAnswer> quizAnswers;

    // Quiz locations
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizLocationAnswer> quizLocationAnswers;

    // Quiz matching pairs
    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    QuizMatchingPairAnswer quizMatchingPairAnswer;
}
