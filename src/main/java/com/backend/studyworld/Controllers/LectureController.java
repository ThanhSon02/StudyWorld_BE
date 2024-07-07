package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.DTO.response.LectureRes;
import com.backend.studyworld.Services.LectureService;
import com.backend.studyworld.Services.VideoService;
import com.backend.studyworld.exceptions.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class LectureController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private LectureService lectureService;
    @PostMapping(value = "/api/lecture/save",consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> save(@RequestParam(name = "video")MultipartFile multipartFile, @RequestParam(name = "lectureName") String lectureName, @RequestParam(name = "videoDuration") int videoDuration, @RequestParam(name = "sectionId") int sectionId ) {
        ApiBaseResponse response = new ApiBaseResponse();
        try {
            Map<String, String> videoMap = videoService.uploadVideo(multipartFile);
            LectureRes lectureRes = lectureService.save(lectureName,videoMap.get("pathStorage"),videoMap.get("originalFileName"),videoDuration, sectionId);
            Map map = new HashMap();
            map.put("lectureSaved", lectureRes);
            response.setMessage("Đã lưu bài giảng");
            response.setData(map);
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Lưu không thành công");
        }
    }

    @PutMapping("/api/lecture/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> update(@RequestParam(name = "lectureId") int lectureId,@RequestParam(name = "video")MultipartFile multipartFile, @RequestParam(name = "lectureName") String lectureName, @RequestParam(name = "videoDuration") int videoDuration ) {
        ApiBaseResponse response = new ApiBaseResponse();
        try {
            String pathStorage = "";
            String originalFileName = "";
            if(!multipartFile.isEmpty() || multipartFile == null) {
                Map<String, String> videoMap = videoService.uploadVideo(multipartFile);
                pathStorage = videoMap.get("pathStorage");
                originalFileName = videoMap.get("originalFileName");
            }
            LectureRes lectureRes = lectureService.update(lectureId, lectureName,pathStorage,originalFileName,videoDuration);
            Map map = new HashMap();
            map.put("lectureSaved", lectureRes);
            response.setMessage("Đã cập nhật bài giảng");
            response.setData(map);
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Cập nhật bài giảng không thành công");
        }
    }

    @DeleteMapping("/api/lecture/delete/{lectureId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> delete(@PathVariable(name = "lectureId") int lectureId) {
        ApiBaseResponse response = new ApiBaseResponse();
        LectureRes lectureRes = lectureService.deleteLecture(lectureId);
        Map map = new HashMap();
        map.put("lectureDeleted", lectureRes);
        response.setMessage("Đã xoá bài giảng");
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/lecture/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> getAllLecture() {
        ApiBaseResponse response = new ApiBaseResponse();
        List<LectureRes> lectureResList = lectureService.getAllLecture();
        Map map = new HashMap();
        map.put("allLecture", lectureResList);
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/teacher/lecture/getAll")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> getAllLectureByTeacher() {
        ApiBaseResponse response = new ApiBaseResponse();
        List<LectureRes> lectureResList = lectureService.getAllLecture();
        Map map = new HashMap();
        map.put("allLecture", lectureResList);
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
