package com.backend.studyworld.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;


@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String courseName;

    private Long price;

    private Long salePrice;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageLink;

    private int categoryId;

    @Column(name = "is_published")
    private boolean publish;

    @Column(name = "is_deleted")
    private boolean delete;

    private Date createTime;

    private Date updateTime;

    private int teacherId;

    public Course(String courseName, Long price, Long salePrice, String description, String imageLink, int categoryId, boolean publish, boolean delete, Date createTime, Date updateTime, int teacherId) {
        this.courseName = courseName;
        this.price = price;
        this.salePrice = salePrice;
        this.description = description;
        this.imageLink = imageLink;
        this.categoryId = categoryId;
        this.publish = publish;
        this.delete = delete;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.teacherId = teacherId;
    }
}
