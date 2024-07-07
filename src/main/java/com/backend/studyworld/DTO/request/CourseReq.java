package com.backend.studyworld.DTO.request;

import lombok.Data;
@Data
public class CourseReq {
    private int id;

    private String courseName;

    private Long price;

    private Long salePrice;

    private String description;

    private String imageLink;

    private int categoryId;

    private boolean publish;

    private boolean delete;

    private int teacherId;
}
