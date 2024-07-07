package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.Services.VideoService;
import com.backend.studyworld.exceptions.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/public/api/video/upload")
    public ResponseEntity<ApiBaseResponse> uploadVideo(@RequestParam("video")MultipartFile videoFile) {
        ApiBaseResponse response = new ApiBaseResponse();
        try {
            Map<String, String> videoMap = videoService.uploadVideo(videoFile);
            response.setHttpStatus(HttpStatus.OK);
            Map map = new HashMap();
            map.put("videoPath", videoMap.get("originalFileName"));
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Upload file không thành công");
        }
    }

    @GetMapping(value = "/public/api/video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos(@PathVariable String title) {
        return videoService.getVideo(title);
    }

}
