package com.sudo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService
{
    String saveFile(MultipartFile file);
    Boolean deleteFile(String urlPath);
}
