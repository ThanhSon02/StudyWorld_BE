package com.backend.studyworld.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionRes {
    private int sectionId;
    private String sectionName;
    private int courseId;
    private String courseName;
    private List<LectureRes> lectureResList;

    public SectionRes(int sectionId, String sectionName, int courseId, String courseName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.courseId = courseId;
        this.courseName = courseName;
    }
}
