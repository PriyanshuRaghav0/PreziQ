package com.bitorax.priziq.controller.rest;

import com.bitorax.priziq.dto.request.file.*;
import com.bitorax.priziq.dto.response.common.ApiResponse;
import com.bitorax.priziq.dto.response.file.MultipleFileResponse;
import com.bitorax.priziq.dto.response.file.SingleFileResponse;
import com.bitorax.priziq.service.S3FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.bitorax.priziq.utils.MetaUtils.buildMetaInfo;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/storage/aws-s3")
public class S3FileStorageController {

    S3FileStorageService s3FileStorageService;

    @PostMapping("/upload/single")
    public ApiResponse<SingleFileResponse> uploadSingleFile(@Valid SingleUploadFileRequest singleUploadFileRequest, HttpServletRequest servletRequest) {
        String folderName = singleUploadFileRequest.getFolderName();
        MultipartFile file = singleUploadFileRequest.getFile();

        String fileUrl = s3FileStorageService.uploadSingleFile(file, folderName);
        SingleFileResponse responseDto = new SingleFileResponse(file.getOriginalFilename(), fileUrl);

        return ApiResponse.<SingleFileResponse>builder()
                .message("Single file uploaded successfully")
                .data(responseDto)
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PostMapping("/upload/multiple")
    public ApiResponse<MultipleFileResponse> uploadMultipleFiles(@Valid MultipleUploadFileRequest multipleUploadFileRequest, HttpServletRequest servletRequest) {
        String folderName = multipleUploadFileRequest.getFolderName();
        List<MultipartFile> files = multipleUploadFileRequest.getFiles();

        List<String> fileUrls = s3FileStorageService.uploadMultipleFiles(files, folderName);
        List<SingleFileResponse> fileResponses = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            fileResponses.add(new SingleFileResponse(files.get(i).getOriginalFilename(), fileUrls.get(i)));
        }

        MultipleFileResponse multipleDto = new MultipleFileResponse(fileResponses);

        return ApiResponse.<MultipleFileResponse>builder()
                .message("Multiple files uploaded successfully")
                .data(multipleDto)
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @DeleteMapping("/delete/single")
    public ApiResponse<String> deleteSingleFile(@RequestParam("filePath") String filePath, HttpServletRequest servletRequest) {
        s3FileStorageService.deleteSingleFile(filePath);
        return ApiResponse.<String>builder()
                .message("Single file deleted successfully")
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @DeleteMapping("/delete/multiple")
    public ApiResponse<String> deleteMultipleFiles(@RequestBody MultipleDeleteFileRequest multipleDeleteFileRequest, HttpServletRequest servletRequest) {
        s3FileStorageService.deleteMultipleFiles(multipleDeleteFileRequest.getFilePaths());
        return ApiResponse.<String>builder()
                .message("Multiple files deleted successfully")
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PutMapping("/move/single")
    public ApiResponse<String> moveSingleFile(@Valid SingleMoveFileRequest singleMoveFileRequest, HttpServletRequest servletRequest) {
        String sourceKey = singleMoveFileRequest.getSourceKey();
        String destinationFolder = singleMoveFileRequest.getDestinationFolder();
        s3FileStorageService.moveSingleFile(sourceKey, destinationFolder);

        return ApiResponse.<String>builder()
                .message("File moved successfully")
                .data("File has been moved from: " + sourceKey + " to folder: " + destinationFolder)
                .meta(buildMetaInfo(servletRequest))
                .build();
    }

    @PutMapping("/move/multiple")
    public ApiResponse<String> moveMultipleFiles(@RequestBody MultipleMoveFileRequest requestDto, HttpServletRequest servletRequest) {
        s3FileStorageService.moveMultipleFiles(requestDto.getSourceKeys(), requestDto.getDestinationFolder());

        return ApiResponse.<String>builder()
                .message("Multiple files moved successfully")
                .data("Files have been moved to folder: " + requestDto.getDestinationFolder())
                .meta(buildMetaInfo(servletRequest))
                .build();
    }
}