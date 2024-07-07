package com.backend.studyworld.Services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();
        }catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean deleteFile(String imgLink) {
        try {
            Map map = new HashMap();
            cloudinary.uploader().destroy(imgLink, map);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
