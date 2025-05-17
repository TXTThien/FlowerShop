package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.VideoImage;
import org.example.repository.VideoImageRepository;
import org.example.service.IVideoImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoImageServiceImpl implements IVideoImageService {
    private final VideoImageRepository videoImageRepository;
    @Override
    public List<VideoImage> findImageByCommentID(Integer id) {
        return videoImageRepository.findVideoImagesByVideoComment_Id(id);
    }
}
