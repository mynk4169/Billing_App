package com.sudo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudo.io.ItemRequest;
import com.sudo.io.ItemResponse;
import com.sudo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController
{
    private final ItemService itemService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/items")
    public ItemResponse addItem(@RequestPart("item")String itemString, @RequestPart("file")MultipartFile file)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;
        System.out.println(itemString);
        try
        {
           itemRequest= objectMapper.readValue(itemString,ItemRequest.class);
           return itemService.add(itemRequest,file);
        }
        catch (JsonProcessingException js)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while parsing the Json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/items")
    public List<ItemResponse> readItems()
    {
        return itemService.fetchItems();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("admin/items/{itemId}")
    public void removeItem(@PathVariable String itemId)
    {
        try
        {
            itemService.deleteItem(itemId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not Found");
        }
    }

}
