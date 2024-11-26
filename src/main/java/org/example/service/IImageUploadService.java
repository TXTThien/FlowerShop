package org.example.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageUploadService {
    String uploadImage(MultipartFile file) throws IOException;

}