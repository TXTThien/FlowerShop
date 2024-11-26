package org.example.service;

import org.example.entity.FlowerImages;

import java.util.List;

public interface IFlowerImageService {
    List<FlowerImages> getAllImages();
    FlowerImages getImageById(Integer id);
    FlowerImages createImage(FlowerImages image);
    FlowerImages updateImage(Integer id, FlowerImages imageDetails);
    void deleteImage(Integer id);
    void disableImage(Integer id);

    List<FlowerImages> findImagesByProductID(int id);
}
