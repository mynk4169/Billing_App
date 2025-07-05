package com.sudo.service.impl;

import com.sudo.entity.CategoryEntity;
import com.sudo.entity.ItemEntity;
import com.sudo.io.ItemRequest;
import com.sudo.io.ItemResponse;
import com.sudo.repository.CategoryRepository;
import com.sudo.repository.ItemRepository;
import com.sudo.service.FileUploadService;
import com.sudo.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService
{
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadService uploadService;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) throws IOException
    {

        String imgUrl =uploadService.saveFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity existingCategory = categoryRepository
                .findByCategoryId(request.getCategoryId())
                .orElseThrow(()->new EntityNotFoundException("Category not Found:"+request.getCategoryId()));
        newItem.setImgUrl(imgUrl);
        newItem.setCategory(existingCategory);
        newItem = itemRepository.save(newItem);
        return convertToResponse(newItem);
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteItem(String itemId)
    {
        ItemEntity itemToDelete = itemRepository.findByItemId(itemId)
                .orElseThrow(()->new EntityNotFoundException("Item not Found:"+itemId));
        boolean isFileDelete = uploadService.deleteFile(itemToDelete.getImgUrl());
        if(isFileDelete)
        {
            itemRepository.delete(itemToDelete);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to Delete Item: "+itemId);
        }

        
    }
    private ItemEntity convertToEntity(ItemRequest request)
    {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }
    private ItemResponse convertToResponse(ItemEntity newItem)
    {
        return ItemResponse.builder().
                itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryId(newItem.getCategory().getCategoryId())
                .categoryName(newItem.getCategory().getName())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdateAt())
                .build();

    }
}
