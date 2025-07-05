package com.sudo.service.impl;

import com.sudo.entity.CategoryEntity;
import com.sudo.io.CategoryRequest;
import com.sudo.io.CategoryResponse;
import com.sudo.repository.CategoryRepository;
import com.sudo.repository.ItemRepository;
import com.sudo.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() +"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
        String imgUrl ="http://localhost:8080/api/v1.0/uploads/"+fileName;
        CategoryEntity newCategoryEntity = convertToEntity(request);
        newCategoryEntity.setImgUrl(imgUrl);
        newCategoryEntity = categoryRepository.save(newCategoryEntity);
        return convertToResponse(newCategoryEntity);
    }

    @Override
    public List<CategoryResponse> listCategories()
    {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return categoryEntityList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(String categoryId)
    {

        System.out.println("printing the category id"+categoryId);
        CategoryEntity categoryEntityToDelete =categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(()->new EntityNotFoundException("CategoryEntity not Found: "+categoryId));
        String imgUrl= categoryEntityToDelete.getImgUrl();
        String fileName= imgUrl.substring(imgUrl.lastIndexOf("/")+1);
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        try{
            Files.deleteIfExists(filePath);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        categoryRepository.delete(categoryEntityToDelete);
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId)
    {
        return convertToResponse(
                categoryRepository.findByCategoryId(categoryId)
                        .orElseThrow(()->new EntityNotFoundException("CategoryEntity Not Found Id:"+categoryId)));
    }

    private CategoryResponse convertToResponse(CategoryEntity categoryEntity)
    {
        Integer itemCount = itemRepository.countByCategoryId(categoryEntity.getId());
        return CategoryResponse.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .bgColor(categoryEntity.getBgColor())
                .imgUrl(categoryEntity.getImgUrl())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .items(itemCount)
                .build();

    }
    private static CategoryEntity convertToEntity(CategoryRequest request)
    {
        return com.sudo.entity.CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
