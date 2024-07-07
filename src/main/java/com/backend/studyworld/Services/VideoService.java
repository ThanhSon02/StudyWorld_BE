package com.backend.studyworld.Services;

import com.backend.studyworld.Repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoService {
    @Autowired
    private LectureRepository lectureRepository;

    @Value("${project.videoStorage}")
    private String pathStorage;

    private static final String FORMAT="classpath:video/%s.mp4";

    @Autowired
    private ResourceLoader resourceLoader;

    public Map<String, String> uploadVideo(MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = pathStorage + File.separator + fileName;

        File folder = new File(pathStorage);
        if(!folder.exists()) {
            folder.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        Map map = new HashMap();
        map.put("pathStorage", fileName);
        map.put("originalFileName",originalFileName);
        return map;
    }

    public Mono<Resource> getVideo(String title){
        return Mono.fromSupplier(()->resourceLoader.
                getResource(String.format(FORMAT,title)))   ;
    }
}
