package com.backend.studyworld.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private int courseId;

    private int userId;

    private int progress;

    private Date enrollDate;

    public Enrollment(int courseId, int userId, int progress, Date enrollDate) {
        this.courseId = courseId;
        this.userId = userId;
        this.progress = progress;
        this.enrollDate = enrollDate;
    }
}
