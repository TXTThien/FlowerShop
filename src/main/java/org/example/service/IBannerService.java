package org.example.service;

import org.example.entity.Banner;

import java.util.List;

public interface IBannerService {
    Banner save(Banner banner);
    Banner findById(Integer id);
    List<Banner> findAll();
    Banner update(Banner banner);
    boolean delete(Integer id);

    List<Banner> find4BannerEnable();
}
