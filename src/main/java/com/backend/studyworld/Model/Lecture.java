package com.backend.studyworld.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String lectureName;

    private String lectureVideo;

    private String videoOriginalName;

    private int videoDuration;

    private int sectionId;

    private boolean isDeleted;

    private int view;
    public Lecture(String lectureName, String lessonVideoLink, String videoOriginalName,int videoDuration,int sectionId) {
        this.lectureName = lectureName;
        this.lectureVideo = lessonVideoLink;
        this.videoOriginalName = videoOriginalName;
        this.videoDuration = videoDuration;
        this.sectionId = sectionId;
    }


}
