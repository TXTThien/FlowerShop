package org.example.service;

import org.example.entity.VideoComment;

import java.util.List;

public interface IVideoCommentService {
    List<VideoComment> findCommentByVidIDAndStatus(int id);

    List<VideoComment> findCommentChild(Integer id);

    VideoComment findVidCommentByIDEnable(int id);
}
