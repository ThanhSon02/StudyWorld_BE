package com.backend.studyworld.Services;

import com.backend.studyworld.DTO.request.CourseReq;
import com.backend.studyworld.DTO.response.LectureRes;
import com.backend.studyworld.DTO.response.SectionRes;
import com.backend.studyworld.Model.Category;
import com.backend.studyworld.Model.Course;
import com.backend.studyworld.DTO.response.CourseRes;
import com.backend.studyworld.Model.Enrollment;
import com.backend.studyworld.Model.User;
import com.backend.studyworld.Repositories.*;
import com.backend.studyworld.exceptions.InternalException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private CourseRes convertToDTO(Course course) {
        CourseRes courseRes = modelMapper.map(course, CourseRes.class);
        return courseRes;
    }
    public List<CourseRes> getAll() {
        try {
            List<CourseRes> courseResList = courseRepository.getAllCourse();
            return courseResList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }

    public CourseRes deleteCourse(int courseId){
        try {
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResolutionException("Không tìm thấy khoá học"));
            course.setDelete(true);
            Course courseSaved = courseRepository.save(course);
            CourseRes result = modelMapper.map(courseSaved, CourseRes.class);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi khi xoá khoá học");
        }
    }

    public CourseRes save(CourseReq courseReq) {
        try {
            Category category = categoriesRepository.findById(courseReq.getCategoryId()).orElseThrow(() -> new ResolutionException("Không tìm thấy danh mục"));
            User user = userRepository.findById(courseReq.getTeacherId()).orElseThrow(() -> new ResolutionException("Không tìm người dùng"));
            Course courseSaved = courseRepository.save(new Course(courseReq.getCourseName(), courseReq.getPrice(), courseReq.getSalePrice(), courseReq.getDescription(), courseReq.getImageLink(), courseReq.getCategoryId(), courseReq.isPublish(), false, new Date(), new Date(), user.getId()));
            CourseRes courseRes = modelMapper.map(courseSaved, CourseRes.class);
            courseRes.setCategoryName(category.getCategoryName());
            courseRes.setTeacherName(user.getName());
            return courseRes;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi khi thêm khoá học");
        }
    }

    public List<CourseRes> getCourseByCategory(int categoryId) {
        try {
            List<CourseRes> courseResList = courseRepository.getAllCourseByCategory(categoryId);
            return courseResList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public CourseRes updateCourse(CourseReq courseReq) {
        try {
            Category category = categoriesRepository.findById(courseReq.getCategoryId()).orElseThrow(() -> new ResolutionException("Không tìm thấy danh mục"));
            Course course = courseRepository.findById(courseReq.getId()).orElseThrow(() -> new ResolutionException("Không tìm thấy khoá học"));
            course.setCourseName(courseReq.getCourseName());
            course.setPrice(courseReq.getPrice());
            course.setDescription(courseReq.getDescription());
            if(courseReq.getImageLink() != null) {
                course.setImageLink(courseReq.getImageLink());
            }
            course.setSalePrice(courseReq.getSalePrice());
            course.setCategoryId(category.getId());
            course.setPublish(courseReq.isPublish());
            course.setUpdateTime(new Date());
            Course courseSaved = courseRepository.save(course);
            CourseRes result = modelMapper.map(courseSaved, CourseRes.class);
            result.setCategoryName(category.getCategoryName());
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Lỗi cập nhật khoá học");
        }
    }

    public CourseRes getCourseById(int courseId) {
        try {
            CourseRes courseRes = courseRepository.getCourseById(courseId);
            List<SectionRes> sectionResList = sectionRepository.getSectionsByCourse(courseId);
            for (SectionRes sectionRes : sectionResList) {
                List<LectureRes> lectureResList = lectureRepository.getLectureBySection(sectionRes.getSectionId());
                sectionRes.setLectureResList(lectureResList);
            }
            courseRes.setSectionResList(sectionResList);
            return courseRes;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Lỗi khi lấy thông tin khoá học");
        }
    }

    public List<CourseRes> getListCourseEnroll(int useId) {
        try {
            List<CourseRes> courseResList = courseRepository.getListCourseEnroll(useId);
            return courseResList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CourseRes getCourseEnrollById(int userId, int courseId) {
        try {
            Enrollment enrollment = null;
            enrollment = enrollmentRepository.checkEnroll(userId, courseId);
            if (enrollment != null) {
                CourseRes courseRes = courseRepository.getCourseById(courseId);
                List<SectionRes> sectionResList = sectionRepository.getSectionsByCourse(courseId);
                for (SectionRes sectionRes : sectionResList) {
                    List<LectureRes> lectureResList = lectureRepository.getLectureDetailBySection(sectionRes.getSectionId());
                    sectionRes.setLectureResList(lectureResList);
                }
                courseRes.setSectionResList(sectionResList);
                return courseRes;
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Lỗi khi lấy thông tin khoá học");
        }
    }
}
