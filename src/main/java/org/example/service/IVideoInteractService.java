package org.example.service;

import org.example.entity.VideoInteract;

import java.util.List;

public interface IVideoInteractService {
    List<VideoInteract> findLikeCommentYet(Integer id, int accountid);

    List<VideoInteract> findLikeVideoYet(int id, int accountid);
}
