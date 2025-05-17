package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.VideoInteract;
import org.example.repository.VideoInteractRepository;
import org.example.service.IVideoInteractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoInteractServiceImpl implements IVideoInteractService {
    private final VideoInteractRepository videoInteractRepository;
    @Override
    public List<VideoInteract> findLikeCommentYet(Integer id, int accountid) {
        return videoInteractRepository.findVideoInteractsByVideoComment_IdAndAccountID_AccountID(id,accountid);
    }

    @Override
    public List<VideoInteract> findLikeVideoYet(int id, int accountid) {
        return videoInteractRepository.findVideoInteractsByVideo_IdAndAccountID_AccountID(id,accountid);
    }
}
