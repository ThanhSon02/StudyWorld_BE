package com.backend.studyworld.Repositories;
import com.backend.studyworld.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT c FROM Category c WHERE c.categoryName like %?1%")
    List<Category> findCategoriesByName(String categoryName) throws Exception;

}
