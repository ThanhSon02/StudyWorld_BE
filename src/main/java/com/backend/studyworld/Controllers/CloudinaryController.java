package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.request.CloudinaryDeleteReq;
import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.Services.CloudinaryService;
import com.backend.studyworld.exceptions.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class CloudinaryController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/api/cloudinary/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> uploadImage(@RequestParam("image")MultipartFile multipartFile) {
        ApiBaseResponse response = new ApiBaseResponse();
        String data = cloudinaryService.uploadFile(multipartFile);
        if(!StringUtils.isEmpty(data)) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageLink", data);
            response.setHttpStatus(HttpStatus.OK);
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new InternalException("Upload ảnh không thành công");
        }

    }

    @DeleteMapping("/api/cloudinary/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_TEACHER')")
    public ResponseEntity<ApiBaseResponse> deleteImage(@RequestBody CloudinaryDeleteReq imageLink) {
        ApiBaseResponse response = new ApiBaseResponse();
        String publicId = imageLink.getImageLink().split("/")[7].split("\\.")[0];
        boolean checkResult = cloudinaryService.deleteFile(publicId);
        if(checkResult) {
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new InternalException("Xoá ảnh không thành công");
        }

    }
}
