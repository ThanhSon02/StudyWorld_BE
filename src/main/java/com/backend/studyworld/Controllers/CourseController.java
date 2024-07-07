package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.request.CourseReq;
import com.backend.studyworld.DTO.response.CourseRes;
import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.Services.CourseService;
import com.backend.studyworld.exceptions.InternalException;
import com.backend.studyworld.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/api/course/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> addCourse(@RequestBody CourseReq courseReq) {
        ApiBaseResponse response = new ApiBaseResponse();
        CourseRes result = courseService.save(courseReq);
        if (result != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("courseSaved", result);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Đã thêm khoá học mới");
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new InternalException("Thêm khoá học thất bại");
        }
    }

    @DeleteMapping("/api/course/delete/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> deleteCourse(@PathVariable int courseId) {
        ApiBaseResponse response = new ApiBaseResponse();
        CourseRes deletedCourse = courseService.deleteCourse(courseId);
        Map map = new HashMap();
        response.setHttpStatus(HttpStatus.OK);
        map.put("courseDeleted", deletedCourse);
        response.setMessage("Đã xoá khoá học");
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/course/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> updateCourse(@RequestBody CourseReq request) {
        ApiBaseResponse response = new ApiBaseResponse();
        CourseRes courseRes = courseService.updateCourse(request);
        if (courseRes != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("courseUpdated", courseRes);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Khoá học đã được cập nhật");
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new InternalException("Cập nhật khoá học thất bại");
        }
    }

    @GetMapping("/public/api/course/getAll")
    public ResponseEntity<ApiBaseResponse> getAllCourse() {
        ApiBaseResponse response = new ApiBaseResponse();
        List<CourseRes> dtoList = courseService.getAll();
        Map<String, Object> map = new HashMap<>();
        map.put("allCourse", dtoList);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/api/course/getAllByCategory/{categoryId}")
    public ResponseEntity<ApiBaseResponse> getAllCourseByCategory(@PathVariable(name = "categoryId") int categoryId) {
        ApiBaseResponse response = new ApiBaseResponse();
        List<CourseRes> dtoList = courseService.getCourseByCategory(categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("allCourseByCategory", dtoList);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/api/course/{courseId}")
    public ResponseEntity<ApiBaseResponse> getCourseById(@PathVariable(name = "courseId") int courseId) {
        ApiBaseResponse response = new ApiBaseResponse();
        CourseRes courseRes = courseService.getCourseById(courseId);
        Map map = new HashMap();
        map.put("course", courseRes);
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/getAllCourse/enroll/{userId}")
    public ResponseEntity<ApiBaseResponse> getListCourseEnroll(@PathVariable(name = "userId") int userId) {
        ApiBaseResponse response = new ApiBaseResponse();
        List<CourseRes> courseResList = courseService.getListCourseEnroll(userId);
        Map map = new HashMap();
        map.put("listCourseEnroll", courseResList);
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/course/enroll/{userId}/{courseId}")
    public ResponseEntity<ApiBaseResponse> getCourseErollById(@PathVariable(name = "userId") int userId, @PathVariable(name = "courseId") int courseId) {
        ApiBaseResponse response = new ApiBaseResponse();
        CourseRes courseRes = courseService.getCourseEnrollById(userId,courseId);
        if (courseRes != null) {
            Map map = new HashMap();
            map.put("courseEnroll", courseRes);
            response.setData(map);
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new NotFoundException("Không tìm thấy khoá học");
        }
    }
}
