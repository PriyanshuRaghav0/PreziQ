package com.bitorax.priziq.service;

import com.bitorax.priziq.dto.request.activity.CreateActivityRequest;
import com.bitorax.priziq.dto.request.activity.UpdateActivityRequest;
import com.bitorax.priziq.dto.request.activity.quiz.*;
import com.bitorax.priziq.dto.request.activity.slide.CreateSlideElementRequest;
import com.bitorax.priziq.dto.request.activity.slide.UpdateSlideElementRequest;
import com.bitorax.priziq.dto.request.activity.slide.UpdateSlideRequest;
import com.bitorax.priziq.dto.response.activity.ActivityDetailResponse;
import com.bitorax.priziq.dto.response.activity.ActivitySummaryResponse;
import com.bitorax.priziq.dto.response.activity.quiz.QuizMatchingPairAnswerResponse;
import com.bitorax.priziq.dto.response.activity.quiz.QuizMatchingPairConnectionResponse;
import com.bitorax.priziq.dto.response.activity.quiz.QuizMatchingPairItemResponse;
import com.bitorax.priziq.dto.response.activity.quiz.QuizResponse;
import com.bitorax.priziq.dto.response.activity.slide.SlideElementResponse;
import com.bitorax.priziq.dto.response.activity.slide.SlideResponse;

import java.util.List;

public interface ActivityService {
    ActivitySummaryResponse createActivity(CreateActivityRequest createActivityRequest);

    ActivityDetailResponse getActivityById(String activityId);

    QuizResponse updateQuiz(String activityId, UpdateQuizRequest updateQuizRequest);

    void deleteActivity(String activityId);

    ActivitySummaryResponse updateActivity(String activityId, UpdateActivityRequest updateActivityRequest);

    SlideResponse updateSlide(String slideId, UpdateSlideRequest updateSlideRequest);

    SlideElementResponse addSlideElement(String slideId, CreateSlideElementRequest createSlideElementRequest);

    SlideElementResponse updateSlideElement(String slideId, String elementId, UpdateSlideElementRequest updateSlideElementRequest);

    void deleteSlideElement(String slideId, String elementId);

    QuizMatchingPairAnswerResponse addMatchingPairItem(String quizId);

    QuizMatchingPairAnswerResponse updateAndReorderMatchingPairItem(String quizId, String itemId, UpdateAndReorderMatchingPairItemRequest request);

    void deleteMatchingPairItem(String quizId, String itemId);

    QuizMatchingPairConnectionResponse addMatchingPairConnection(String quizId, CreateMatchingPairConnectionRequest request);

    void deleteMatchingPairConnection(String quizId, String connectionId);

    public void createDefaultQuizButtonsActivity(String collectionId);
}
