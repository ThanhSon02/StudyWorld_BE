package com.backend.studyworld.Repositories;

import com.backend.studyworld.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    @Query("select e from Enrollment e where e.userId = ?1 and e.courseId = ?2")
    Enrollment checkEnroll(int userId, int courseId);
}
