package com.backend.studyworld.Repositories;

import com.backend.studyworld.DTO.response.SectionRes;
import com.backend.studyworld.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    @Query("SELECT new com.backend.studyworld.DTO.response.SectionRes(s.id, s.sectionName, s.courseId, c.courseName) from Section s LEFT JOIN Course c on s.courseId = c.id where c.delete = false and s.isDeleted = false")
    List<SectionRes> getAllSection();

    @Query("SELECT new com.backend.studyworld.DTO.response.SectionRes(s.id, s.sectionName, s.courseId, c.courseName) from Section s LEFT JOIN Course c on s.courseId = c.id where c.delete = false and s.isDeleted = false and c.teacherId = ?1")
    List<SectionRes> getAllSectionByTeacher(int teacherId);

    @Query("SELECT new com.backend.studyworld.DTO.response.SectionRes(s.id, s.sectionName, s.courseId, c.courseName) from Section s LEFT JOIN Course c on s.courseId = c.id where c.delete = false and c.id = ?1")
    List<SectionRes> getSectionsByCourse(int courseId);
}


