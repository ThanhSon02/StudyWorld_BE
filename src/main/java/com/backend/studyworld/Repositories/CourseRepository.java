package com.backend.studyworld.Repositories;

import com.backend.studyworld.DTO.response.CourseRes;
import com.backend.studyworld.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("select new com.backend.studyworld.DTO.response.CourseRes(co.id, co.courseName, co.price, co.salePrice, co.description,co.imageLink, co.categoryId, ca.categoryName,co.publish, co.delete, co.teacherId, u.name) from Course co LEFT JOIN Category ca ON co.categoryId  = ca.id LEFT JOIN User u ON co.teacherId = u.id WHERE co.delete = false AND ca.id = ?1")
    List<CourseRes> getAllCourseByCategory(int categoryId);

    @Query("select new com.backend.studyworld.DTO.response.CourseRes(co.id, co.courseName, co.price, co.salePrice, co.description,co.imageLink, co.categoryId, ca.categoryName,co.publish, co.delete, co.teacherId, u.name) from Course co LEFT JOIN Category ca ON co.categoryId  = ca.id LEFT JOIN User u ON co.teacherId = u.id WHERE co.delete = false")
    List<CourseRes> getAllCourse();

    @Query("select new com.backend.studyworld.DTO.response.CourseRes(co.id, co.courseName, co.price, co.salePrice, co.description,co.imageLink, co.categoryId, ca.categoryName,co.publish, co.delete, co.teacherId, u.name) from Course co LEFT JOIN Category ca ON co.categoryId  = ca.id LEFT JOIN User u ON co.teacherId = u.id WHERE co.delete = false and co.id = ?1")
    CourseRes getCourseById(int courseId);

    @Query("select new com.backend.studyworld.DTO.response.CourseRes(co.id, co.courseName, co.price, co.salePrice, co.description,co.imageLink, co.categoryId, ca.categoryName,co.publish, co.delete, co.teacherId, u.name, e.progress) from Course co LEFT JOIN Category ca ON co.categoryId  = ca.id LEFT JOIN User u ON co.teacherId = u.id LEFT JOIN Enrollment e on co.id = e.courseId WHERE co.delete = false and e.userId = ?1")
    List<CourseRes> getListCourseEnroll(int userId);
}
