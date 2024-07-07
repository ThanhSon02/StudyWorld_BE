package com.backend.studyworld.Services;

import com.backend.studyworld.DTO.response.EnrollmentRes;
import com.backend.studyworld.Model.Course;
import com.backend.studyworld.Model.Enrollment;
import com.backend.studyworld.Model.User;
import com.backend.studyworld.Repositories.CourseRepository;
import com.backend.studyworld.Repositories.EnrollmentRepository;
import com.backend.studyworld.Repositories.UserRepository;
import com.backend.studyworld.exceptions.InternalException;
import com.backend.studyworld.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EnrollmentServive {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();
    public EnrollmentRes enroll(int courseId, int userId, int progress, Date enrollDate) {
        try {
            Course course = courseRepository.findById(courseId).orElseThrow(() -> {
                throw new NotFoundException("Khoá học không tồn tại");
            });
            User user = userRepository.findById(userId).orElseThrow(() -> {
                throw new NotFoundException("Người dùng không tồn tại");
            });
            Enrollment enrollment = enrollmentRepository.save(new Enrollment(courseId, userId, 0, new Date()));
            EnrollmentRes enrollmentRes = modelMapper.map(enrollment,EnrollmentRes.class);
            enrollmentRes.setCourseName(course.getCourseName());
            enrollmentRes.setUserEmail(user.getEmail());
            enrollmentRes.setUserName(user.getName());
            return enrollmentRes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }
}
