package org.example.service;

import org.example.entity.Video;

import java.util.List;

public interface IVideoService {
    Video findVideoByIDEnable(int id);

    List<Video> findAllEnable();

    List<Video> findVideoByAccountIDEnable(int accountid);
}
