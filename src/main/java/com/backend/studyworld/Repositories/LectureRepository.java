package com.backend.studyworld.Repositories;

import com.backend.studyworld.DTO.response.LectureRes;
import com.backend.studyworld.Model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    @Query("SELECT new com.backend.studyworld.DTO.response.LectureRes(l.id, l.lectureName, l.videoOriginalName,l.videoDuration) from Lecture l LEFT JOIN Section s on l.sectionId = s.id where s.id = ?1")
    List<LectureRes> getLectureBySection(int sectionId);

    @Query("SELECT new com.backend.studyworld.DTO.response.LectureRes(l.id, l.lectureName, l.lectureVideo, l.videoOriginalName,l.videoDuration) from Lecture l LEFT JOIN Section s on l.sectionId = s.id where s.id = ?1")
    List<LectureRes> getLectureDetailBySection(int sectionId);

    @Query("SELECT new com.backend.studyworld.DTO.response.LectureRes(l.id, l.lectureName, l.videoOriginalName,l.videoDuration, s.id, s.sectionName, c.id, c.courseName) from Lecture l LEFT JOIN Section s on l.sectionId = s.id LEFT JOIN Course c on s.courseId = c.id")
    List<LectureRes> getAll();

    @Query("SELECT new com.backend.studyworld.DTO.response.LectureRes(l.id, l.lectureName, l.lectureVideo, l.videoOriginalName,l.videoDuration, s.id, s.sectionName, c.id, c.courseName) from Lecture l LEFT JOIN Section s on l.sectionId = s.id LEFT JOIN Course c on s.courseId = c.id where c.teacherId = ?1")
    List<LectureRes> getAllByTeacher(int teacherId);
}
