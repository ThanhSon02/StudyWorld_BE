package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.request.SectionReq;
import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.DTO.response.SectionRes;
import com.backend.studyworld.Services.SectionService;
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
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @GetMapping("/api/section/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> getAllSection() {
        ApiBaseResponse response = new ApiBaseResponse();
        List<SectionRes> sectionResList = sectionService.getAllSection();
        Map map = new HashMap();
        map.put("sections", sectionResList);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/section/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> save(@RequestBody SectionReq sectionReq) {
        ApiBaseResponse response = new ApiBaseResponse();
        SectionRes sectionRes = sectionService.save(sectionReq);
        Map map = new HashMap();
        map.put("sectionSaved", sectionRes);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        response.setMessage("Đã lưu học phần");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/section/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> update(@RequestBody SectionReq sectionReq) {
        ApiBaseResponse response = new ApiBaseResponse();
        SectionRes sectionRes = sectionService.update(sectionReq);
        Map map = new HashMap();
        map.put("sectionUpdated", sectionRes);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        response.setMessage("Đã cập nhật học phần");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/section/delete/{sectionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> delete(@PathVariable int sectionId) {
        ApiBaseResponse response = new ApiBaseResponse();
        SectionRes sectionResDeleted = sectionService.deleteSection(sectionId);
        Map map = new HashMap();
        map.put("sectionDeleted", sectionResDeleted);
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("Đã xoá thành công học phần");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/section/course/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> getSectionsByCourse(@PathVariable int courseId) {
        ApiBaseResponse response = new ApiBaseResponse();
        List<SectionRes> sectionResList = sectionService.getSectionsByCourse(courseId);
        Map map = new HashMap();
        map.put("sections", sectionResList);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
