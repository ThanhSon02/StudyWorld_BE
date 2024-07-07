package com.backend.studyworld.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionReq {
    private int sectionId;

    private String sectionName;

    private int courseId;
}
