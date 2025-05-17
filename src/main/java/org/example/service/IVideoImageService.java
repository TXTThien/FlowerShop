package org.example.service;

import org.example.entity.VideoImage;

import java.util.List;

public interface IVideoImageService {
    List<VideoImage> findImageByCommentID(Integer id);
}
