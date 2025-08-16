package com.bitorax.priziq.controller.rest;

import com.bitorax.priziq.constant.ActivityType;
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
import com.bitorax.priziq.dto.response.activity.ActivityTypeInfo;
import com.bitorax.priziq.dto.response.common.ApiResponse;
import com.bitorax.priziq.service.ActivityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bitorax.priziq.utils.MetaUtils.buildMetaInfo;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/api/v1")
public class ActivityController {
    ActivityService activityService;

    @PostMapping("/activities")
    ApiResponse<ActivitySummaryResponse> createActivity(@RequestBody @Valid CreateActivityRequest createActivityRequest, HttpServletRequest servletRequest){
        return ApiResponse.<ActivitySummaryResponse>builder()
                .message("Activity created successfully")
                .data(activityService.createActivity(createActivityRequest))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @GetMapping("/activities/{activityId}")
    ApiResponse<ActivityDetailResponse> getActivityById(@PathVariable String activityId, HttpServletRequest servletRequest){
        return ApiResponse.<ActivityDetailResponse>builder()
                .message("Activity detail retrieved successfully")
                .data(activityService.getActivityById(activityId))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @GetMapping("/activities/types")
    ApiResponse<List<ActivityTypeInfo>> getAllActivityTypes(HttpServletRequest servletRequest){
        return ApiResponse.<List<ActivityTypeInfo>>builder()
                .message("Retrieved the list of activity types successfully")
                .data(ActivityType.getAllTypes())
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PutMapping("/activities/{activityId}/quiz")
    ApiResponse<QuizResponse> updateQuiz(@PathVariable String activityId, @RequestBody @Valid UpdateQuizRequest updateQuizRequest, HttpServletRequest servletRequest) {
        return ApiResponse.<QuizResponse>builder()
                .message("Quiz updated successfully")
                .data(activityService.updateQuiz(activityId, updateQuizRequest))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @DeleteMapping("/activities/{activityId}")
    ApiResponse<Void> deleteActivity(@PathVariable String activityId, HttpServletRequest servletRequest) {
        activityService.deleteActivity(activityId);
        return ApiResponse.<Void>builder()
                .message("Activity deleted successfully")
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PutMapping("/activities/{activityId}")
    ApiResponse<ActivitySummaryResponse> updateActivity(@PathVariable String activityId, @RequestBody @Valid UpdateActivityRequest updateActivityRequest, HttpServletRequest servletRequest) {
        return ApiResponse.<ActivitySummaryResponse>builder()
                .message("Activity updated successfully")
                .data(activityService.updateActivity(activityId, updateActivityRequest))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PutMapping("/slides/{slideId}")
    ApiResponse<SlideResponse> updateSlide(@PathVariable String slideId, @RequestBody @Valid UpdateSlideRequest updateSlideRequest, HttpServletRequest servletRequest) {
        return ApiResponse.<SlideResponse>builder()
                .message("Slide updated successfully")
                .data(activityService.updateSlide(slideId, updateSlideRequest))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PostMapping("/slides/{slideId}/elements")
    ApiResponse<SlideElementResponse> addSlideElement(@PathVariable String slideId, @RequestBody @Valid CreateSlideElementRequest createSlideElementRequest, HttpServletRequest servletRequest) {
        return ApiResponse.<SlideElementResponse>builder()
                .message("Slide element added successfully")
                .data(activityService.addSlideElement(slideId, createSlideElementRequest))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PutMapping("/slides/{slideId}/elements/{elementId}")
    ApiResponse<SlideElementResponse> updateSlideElement(@PathVariable String slideId, @PathVariable String elementId, @RequestBody @Valid UpdateSlideElementRequest updateSlideElementRequest, HttpServletRequest servletRequest) {
        return ApiResponse.<SlideElementResponse>builder()
                .message("Slide element updated successfully")
                .data(activityService.updateSlideElement(slideId, elementId, updateSlideElementRequest))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @DeleteMapping("/slides/{slideId}/elements/{elementId}")
    ApiResponse<Void> deleteSlideElement(@PathVariable String slideId, @PathVariable String elementId, HttpServletRequest servletRequest) {
        activityService.deleteSlideElement(slideId, elementId);
        return ApiResponse.<Void>builder()
                .message("Slide element deleted successfully")
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PostMapping("/quizzes/{quizId}/matching-pairs/items")
    ApiResponse<QuizMatchingPairAnswerResponse> addMatchingPairItem(
            @PathVariable String quizId,
            HttpServletRequest servletRequest
    ) {
        return ApiResponse.<QuizMatchingPairAnswerResponse>builder()
                .message("Matching pair item added successfully")
                .data(activityService.addMatchingPairItem(quizId))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PatchMapping("/quizzes/{quizId}/matching-pairs/items/{itemId}")
    ApiResponse<QuizMatchingPairAnswerResponse> updateAndReorderMatchingPairItem(
            @PathVariable String quizId,
            @PathVariable String itemId,
            @RequestBody @Valid UpdateAndReorderMatchingPairItemRequest request,
            HttpServletRequest servletRequest
    ) {
        return ApiResponse.<QuizMatchingPairAnswerResponse>builder()
                .message("Matching pair item updated and reordered successfully")
                .data(activityService.updateAndReorderMatchingPairItem(quizId, itemId, request))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @DeleteMapping("/quizzes/{quizId}/matching-pairs/items/{itemId}")
    ApiResponse<Void> deleteMatchingPairItem(
            @PathVariable String quizId,
            @PathVariable String itemId,
            HttpServletRequest servletRequest
    ) {
        activityService.deleteMatchingPairItem(quizId, itemId);
        return ApiResponse.<Void>builder()
                .message("Matching pair item deleted successfully")
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PostMapping("/quizzes/{quizId}/matching-pairs/connections")
    ApiResponse<QuizMatchingPairConnectionResponse> addMatchingPairConnection(
            @PathVariable String quizId,
            @RequestBody @Valid CreateMatchingPairConnectionRequest request,
            HttpServletRequest servletRequest
    ) {
        return ApiResponse.<QuizMatchingPairConnectionResponse>builder()
                .message("Matching pair connection added successfully")
                .data(activityService.addMatchingPairConnection(quizId, request))
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @DeleteMapping("/quizzes/{quizId}/matching-pairs/connections/{connectionId}")
    ApiResponse<Void> deleteMatchingPairConnection(
            @PathVariable String quizId,
            @PathVariable String connectionId,
            HttpServletRequest servletRequest
    ) {
        activityService.deleteMatchingPairConnection(quizId, connectionId);
        return ApiResponse.<Void>builder()
                .message("Matching pair connection deleted successfully")
                .meta(buildMetaInfo(servletRequest))
                .build();
    }
}
