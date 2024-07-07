package com.backend.studyworld.Services;
import com.backend.studyworld.DTO.response.CategoryRes;
import com.backend.studyworld.Model.Category;
import com.backend.studyworld.Repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Category> findAll() {
        List<Category> categories = categoriesRepository.findAll();
        return categories;
    }

    public Category save(String categoryName) throws Exception{
        Category categoryAdded = categoriesRepository.save(new Category(categoryName));
        return categoryAdded;
    }

    public List<Category> findByCategoryName (String categoryName) throws Exception{
        List<Category> categories = categoriesRepository.findCategoriesByName(categoryName);
        return categories;
    }

    public void deleteCategory (int categoryId) {
        categoriesRepository.deleteById(categoryId);
    }

    public Category updateCategory(CategoryRes categoryReq) {
        Category category1 = categoriesRepository.findById(categoryReq.getId()).orElseThrow(() -> new ResolutionException("Không tìm thấy danh mục"));
        category1.setCategoryName(categoryReq.getCategoryName());
        return categoriesRepository.save(category1);
    }
}
