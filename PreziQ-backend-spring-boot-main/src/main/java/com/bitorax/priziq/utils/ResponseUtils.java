package com.bitorax.priziq.utils;

import com.bitorax.priziq.dto.response.common.ApiResponse;
import com.bitorax.priziq.dto.response.common.MetaInfo;
import com.bitorax.priziq.exception.ErrorCode;
import com.bitorax.priziq.exception.ErrorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResponseUtils {

    public static void sendErrorResponse(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull ObjectMapper objectMapper, @NonNull ErrorCode errorCode) throws IOException {
        // Build MetaInfo
        MetaInfo meta = MetaInfo.builder()
                .timestamp(Instant.now().toString())
                .instance(request.getRequestURI())
                .build();

        // Build ErrorDetail
        ErrorDetail errorDetail = ErrorDetail.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        // Build ApiResponse
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(false)
                .errors(List.of(errorDetail))
                .meta(meta)
                .build();

        // Set response properties
        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Write JSON response
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }

}
