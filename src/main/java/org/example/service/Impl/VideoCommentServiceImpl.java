package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.VideoComment;
import org.example.entity.enums.Status;
import org.example.repository.VideoCommentRepository;
import org.example.service.IVideoCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoCommentServiceImpl implements IVideoCommentService {
    private final VideoCommentRepository videoCommentRepository;
    @Override
    public List<VideoComment> findCommentByVidIDAndStatus(int id) {
        return videoCommentRepository.findVideoCommentsByVideo_IdAndStatus(id, Status.ENABLE);
    }

    @Override
    public List<VideoComment> findCommentChild(Integer id) {
        return videoCommentRepository.findVideoCommentsByFatherComment_IdAndStatus(id,Status.ENABLE);
    }

    @Override
    public VideoComment findVidCommentByIDEnable(int id) {
        return videoCommentRepository.findVideoCommentByIdAndStatus(id,Status.ENABLE);
    }
}
