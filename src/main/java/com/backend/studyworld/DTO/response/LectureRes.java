package com.backend.studyworld.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureRes {
    private int id;

    private String lectureName;

    private String lectureVideo;

    private String videoOriginalName;

    private int videoDuration;

    private int sectionId;

    private String sectionName;

    private int courseId;

    private String courseName;

    public LectureRes(int id, String lectureName, String lectureVideo, String videoOriginalName, int videoDuration) {
        this.id = id;
        this.lectureName = lectureName;
        this.lectureVideo = lectureVideo;
        this.videoOriginalName = videoOriginalName;
        this.videoDuration = videoDuration;
    }

    public LectureRes(int id, String lectureName, String videoOriginalName, int videoDuration) {
        this.id = id;
        this.lectureName = lectureName;
        this.videoOriginalName = videoOriginalName;
        this.videoDuration = videoDuration;
    }

    public LectureRes(int id, String lectureName, String videoOriginalName, int videoDuration, int sectionId, String sectionName, int courseId, String courseName) {
        this.id = id;
        this.lectureName = lectureName;
        this.videoOriginalName = videoOriginalName;
        this.videoDuration = videoDuration;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.courseId = courseId;
        this.courseName = courseName;
    }
}
