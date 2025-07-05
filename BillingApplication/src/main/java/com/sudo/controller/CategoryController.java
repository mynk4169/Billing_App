package com.sudo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudo.io.CategoryRequest;
import com.sudo.io.CategoryResponse;
import com.sudo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController
{

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }
    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file") MultipartFile file)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest request=null;
        try
        {
            request = objectMapper.readValue(categoryString,CategoryRequest.class);
            return categoryService.add(request,file);
        }
        catch(JsonProcessingException   e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception Occured while parsing the json");
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> listCategories()
    {
        return categoryService.listCategories();
    }

    @DeleteMapping(path="admin/categories/{category_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("category_id")String category_id)
    {
        try
        {
            System.out.println(category_id);
            categoryService.delete(category_id);
        }catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="admin/categories/{category_id}")
    public CategoryResponse getCategoryById(@PathVariable("category_id")String id)
    {
        return categoryService.getCategoryById(id);
    }
}
