package com.backend.studyworld.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRes {
    private int id;

    private int courseId;

    private int userId;

    private boolean cancelled;

    private String cancellationReason;

    private String courseName;

    private String userName;

    private String userEmail;
}
