package com.bitorax.priziq.domain.activity.quiz;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "quiz_matching_pair_answers")
public class QuizMatchingPairAnswer {
    @Id
    String quizMatchingPairAnswerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @MapsId
    Quiz quiz;

    @Column(columnDefinition = "TEXT")
    String leftColumnName;

    @Column(columnDefinition = "TEXT")
    String rightColumnName;

    @OneToMany(mappedBy = "quizMatchingPairAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizMatchingPairItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "quizMatchingPairAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizMatchingPairConnection> connections = new ArrayList<>();
}