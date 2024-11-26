package org.example.service.Impl;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.example.service.IImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements IImageUploadService {

    private final String bucketName = "webproject-65086.appspot.com";
    private final Storage storage;

    public ImageUploadServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String folderName = "product/";
        String fileName = folderName + UUID.randomUUID().toString();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        Blob blob = storage.create(blobInfo, file.getInputStream());

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

}