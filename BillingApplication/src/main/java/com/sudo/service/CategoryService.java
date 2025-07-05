package com.sudo.service;

import com.sudo.io.CategoryRequest;
import com.sudo.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService
{
     CategoryResponse add(CategoryRequest request, MultipartFile file)throws IOException;
     List<CategoryResponse>listCategories();
     void delete(String CategoryId);
     CategoryResponse getCategoryById(String Id);
}
