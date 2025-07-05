package com.sudo.service.impl;

import com.sudo.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public String saveFile(MultipartFile file)
    {
        String fileName = UUID.randomUUID() +"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path targetLocation = uploadPath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "http://localhost:8080/api/v1.0/uploads/"+fileName;
    }

    @Override
    public Boolean deleteFile(String urlPath) {
        String fileName = urlPath.substring(urlPath.lastIndexOf("/")+1);
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        try
        {
            Files.deleteIfExists(filePath);

        }
        catch (IOException io)
        {
            throw new RuntimeException("IO Exception:"+io.getMessage());
        }
        return true;
    }
}
