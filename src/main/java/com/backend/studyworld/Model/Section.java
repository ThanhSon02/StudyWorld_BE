package com.backend.studyworld.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String sectionName;

    private int courseId;

    private boolean isDeleted;

    public Section(String sectionName, int courseId) {
        this.sectionName = sectionName;
        this.courseId = courseId;
    }
}
