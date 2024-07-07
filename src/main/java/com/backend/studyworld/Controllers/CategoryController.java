package com.backend.studyworld.Controllers;
import com.backend.studyworld.DTO.response.CategoryRes;
import com.backend.studyworld.Model.Category;
import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.Services.CategoriesService;
import com.backend.studyworld.exceptions.InternalException;
import com.backend.studyworld.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoriesService categoriesService;
    @GetMapping("/public/api/category/getAll")
    public ResponseEntity<ApiBaseResponse> getAll() {
        ApiBaseResponse response = new ApiBaseResponse();
        try {
            List<Category> categoryList = categoriesService.findAll();
            Map<String, Object> map = new HashMap<>();
            map.put("all_category", categoryList);
            response.setData(map);
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Đã xảy ra lỗi");
        }
    }

    @PostMapping("/api/category/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiBaseResponse> addCategory(@RequestBody CategoryRes categoryDto) {
        ApiBaseResponse response = new ApiBaseResponse();
        try {
            if(!StringUtils.isEmpty(categoryDto.getCategoryName())) {
                Category category = categoriesService.save(categoryDto.getCategoryName());
                Map<String, Object> map = new HashMap<>();
                map.put("category", category);
                response.setData(map);
                response.setMessage("Thêm danh mục thành công");
                response.setHttpStatus(HttpStatus.OK);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new NotFoundException("Danh mục không tồn tại");
            }
        } catch (Exception e) {
            throw new InternalException("Đã xảy ra lỗi");
        }
    }

    @GetMapping("/public/api/category")
    public ResponseEntity<ApiBaseResponse> getCategoriesById(@RequestParam(value = "name") String name) {
        ApiBaseResponse response = new ApiBaseResponse();
        try {
            List<Category> categories = categoriesService.findByCategoryName(name);
            Map<String, Object> map = new HashMap<>();
            map.put("categories", categories);
            response.setHttpStatus(HttpStatus.OK);
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException("Danh mục không tồn tại");
        }
    }

    @PutMapping("/api/category/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiBaseResponse> updateCategory(@RequestBody CategoryRes request) {
        ApiBaseResponse response = new ApiBaseResponse();
        if(StringUtils.isEmpty(request.getCategoryName())) {
            throw new NotFoundException("Danh mục không tồn tại");
        }
        try {
            Category result = categoriesService.updateCategory(request);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Cập nhật danh mục thành công");
            Map<String, Object> map = new HashMap<>();
            map.put("categoryUpdated", result);
            response.setData(map);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            throw new InternalException("Cập nhật danh mục thất bại");
        }
    }

    @DeleteMapping("/api/category/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiBaseResponse> deleteCategory(@PathVariable int id) {
        ApiBaseResponse response = new ApiBaseResponse();
        try{
            categoriesService.deleteCategory(id);
            Map map = new HashMap();
            map.put("categoryDeleted", id);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Xoá danh mục thành công");
            response.setData(map);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Xoá danh mục thất bại");
        }
    }
}
