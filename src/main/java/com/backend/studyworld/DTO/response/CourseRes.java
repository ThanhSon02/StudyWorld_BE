package com.backend.studyworld.DTO.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRes {
    private int id;

    private String courseName;

    private Long price;

    private Long salePrice;

    private String description;

    private String imageLink;

    private int categoryId;

    private boolean publish;

    private boolean delete;

    private Date createTime;

    private Date updateTime;

    private String categoryName;

    private int teacherId;

    private String teacherName;

    private int progress;

    private List<SectionRes> sectionResList;

    public CourseRes(int id, String courseName, Long price, Long salePrice, String description, String imageLink, int categoryId, String categoryName,boolean publish, boolean delete, int teacherId, String teacherName) {
        this.id = id;
        this.courseName =courseName;
        this.price = price;
        this.salePrice = salePrice;
        this.description =description;
        this.imageLink = imageLink;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publish = publish;
        this.delete = delete;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public CourseRes(int id, String courseName, Long price, Long salePrice, String description, String imageLink, int categoryId, String categoryName,boolean publish, boolean delete, int teacherId, String teacherName, int progress) {
        this.id = id;
        this.courseName =courseName;
        this.price = price;
        this.salePrice = salePrice;
        this.description =description;
        this.imageLink = imageLink;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publish = publish;
        this.delete = delete;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.progress = progress;
    }

    public CourseRes(int id, String courseName, Long price, Long salePrice, String description, String imageLink, int categoryId, String categoryName,boolean publish, boolean delete) {
        this.id = id;
        this.courseName =courseName;
        this.price = price;
        this.salePrice = salePrice;
        this.description =description;
        this.imageLink = imageLink;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publish = publish;
        this.delete = delete;
    }

}
