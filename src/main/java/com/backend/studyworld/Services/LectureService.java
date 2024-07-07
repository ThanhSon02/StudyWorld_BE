package com.backend.studyworld.Services;

import com.backend.studyworld.DTO.response.LectureRes;
import com.backend.studyworld.Model.Lecture;
import com.backend.studyworld.Model.Section;
import com.backend.studyworld.Repositories.LectureRepository;
import com.backend.studyworld.Repositories.SectionRepository;
import com.backend.studyworld.exceptions.InternalException;
import com.backend.studyworld.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class LectureService {
    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private SectionRepository sectionRepository;
    private ModelMapper modelMapper = new ModelMapper();
    public LectureRes save(String lectureName, String lectureVideo, String videoOriginalName,int videoDuration,int sectionId) {
        try {
            Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new NotFoundException("Không tìm thấy học phần"));
            Lecture lectureSaved = lectureRepository.save(new Lecture(lectureName, lectureVideo, videoOriginalName,videoDuration,section.getId()));
            LectureRes lectureRes = modelMapper.map(lectureSaved, LectureRes.class);
            lectureRes.setSectionName(section.getSectionName());
            return lectureRes;
        }catch (Exception e) {
            throw new InternalException("Lỗi khi thêm bài giảng");
        }
    }

    public LectureRes update(int lectureId, String lectureName, String lectureVideo, String videoNameStorage,int videoDuration) {
        try {
            Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new NotFoundException("Không tìm thấy bài giảng"));
            Section section = sectionRepository.findById(lecture.getSectionId()).orElseThrow(() -> new NotFoundException("Không tìm thấy học phần"));
            lecture.setLectureName(lectureName);
            if(!StringUtils.isEmpty(lectureVideo) && !StringUtils.isEmpty(lectureVideo) && videoDuration != 0) {
                lecture.setLectureVideo(lectureVideo);
                lecture.setVideoOriginalName(videoNameStorage);
                lecture.setVideoDuration(videoDuration);
            }
            Lecture lectureSaved = lectureRepository.save(lecture);
            LectureRes lectureRes = modelMapper.map(lectureSaved, LectureRes.class);
            lectureRes.setSectionName(section.getSectionName());
            return lectureRes;
        }catch (Exception e) {
            throw new InternalException("Lỗi khi cập nhật bài giảng");
        }
    }

    public List<LectureRes> getAllLecture() {
        try {
            List<LectureRes> allLecture = lectureRepository.getAll();
            return allLecture;
        }catch (Exception e) {
            throw new InternalException("Lỗi khi thêm bài giảng");
        }
    }

    public List<LectureRes> getAllLectureByTeacher(int teacherId) {
        try {
            List<LectureRes> allLecture = lectureRepository.getAllByTeacher(teacherId);
            return allLecture;
        }catch (Exception e) {
            throw new InternalException("Lỗi khi thêm bài giảng");
        }
    }

    public LectureRes deleteLecture(int lectureId) {
        try {
            Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new NotFoundException("Không tìm thấy bài giảng"));
            lecture.setDeleted(true);
            Lecture lectureSaved = lectureRepository.save(lecture);
            LectureRes lectureRes = modelMapper.map(lectureSaved, LectureRes.class);
            return lectureRes;
        }catch (Exception e) {
            throw new InternalException("Lỗi khi thêm bài giảng");
        }
    }
}
