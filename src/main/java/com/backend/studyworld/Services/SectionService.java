package com.backend.studyworld.Services;

import com.backend.studyworld.DTO.request.SectionReq;
import com.backend.studyworld.DTO.response.SectionRes;
import com.backend.studyworld.Model.Course;
import com.backend.studyworld.Model.Section;
import com.backend.studyworld.Repositories.CourseRepository;
import com.backend.studyworld.Repositories.LectureRepository;
import com.backend.studyworld.Repositories.SectionRepository;
import com.backend.studyworld.exceptions.InternalException;
import com.backend.studyworld.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private LectureRepository lectureRepository;
    private ModelMapper modelMapper = new ModelMapper();
    public SectionRes save(SectionReq sectionReq) {
        try {
            Course course = courseRepository.findById(sectionReq.getCourseId()).orElseThrow(() -> new NotFoundException("Không tìm thấy khoá hoc"));
            Section sectionSaved = sectionRepository.save(new Section(sectionReq.getSectionName(), course.getId()));
            SectionRes sectionRes = modelMapper.map(sectionSaved, SectionRes.class);
            sectionRes.setCourseName(course.getCourseName());
            return sectionRes;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi khi thêm học phần");
        }
    }

    public SectionRes update(SectionReq sectionReq) {
        try {
            Section section = sectionRepository.findById(sectionReq.getSectionId()).orElseThrow(() -> {
                throw new NotFoundException("Học phần không tồn tại");
            });
            Course course = courseRepository.findById(section.getCourseId()).orElseThrow(() -> new NotFoundException("Không tìm thấy khoá hoc"));
            section.setSectionName(sectionReq.getSectionName());
            Section sectionSaved = sectionRepository.save(section);
            SectionRes sectionRes = modelMapper.map(sectionSaved, SectionRes.class);
            sectionRes.setCourseName(course.getCourseName());
            return sectionRes;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi khi cập nhật học phần");
        }
    }

    public List<SectionRes> getAllSection() {
        try {
            List<SectionRes> sectionResList = sectionRepository.getAllSection();
            return sectionResList;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }

    public List<SectionRes> getAllSectionByTeacher(int teacherId) {
        try {
            List<SectionRes> sectionResList = sectionRepository.getAllSectionByTeacher(teacherId);
            return sectionResList;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }

    public SectionRes deleteSection(int sectionId) {
        try {
            Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new NotFoundException("Không tìm thấy học phần"));
            section.setDeleted(true);
            Section sectionSaved = sectionRepository.save(section);
            SectionRes sectionRes = modelMapper.map(sectionSaved, SectionRes.class);
            return sectionRes;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }

    public List<SectionRes> getSectionsByCourse(int courseId) {
        try {
            List<SectionRes> sectionResList = sectionRepository.getSectionsByCourse(courseId);
            return sectionResList;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }
}
