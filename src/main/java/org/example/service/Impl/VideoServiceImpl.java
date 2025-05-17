package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Video;
import org.example.entity.enums.Status;
import org.example.repository.VideoRepository;
import org.example.service.IVideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements IVideoService {
    private final VideoRepository videoRepository;
    @Override
    public Video findVideoByIDEnable(int id) {
        return videoRepository.findVideoByIdAndStatus(id, Status.ENABLE);
    }

    @Override
    public List<Video> findAllEnable() {
        return videoRepository.findVideosByStatus(Status.ENABLE);
    }

    @Override
    public List<Video> findVideoByAccountIDEnable(int accountid) {
        return videoRepository.findVideosByAccountID_AccountIDAndStatus(accountid,Status.ENABLE);
    }
}
