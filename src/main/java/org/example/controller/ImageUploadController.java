package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.service.IImageUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class ImageUploadController {
    private final IImageUploadService imageUploadService;

    @PostMapping("")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            String link = imageUploadService.uploadImage(file);
            response.put("DT", link);
            response.put("EM", "success");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("DT", null);
            response.put("EM", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}