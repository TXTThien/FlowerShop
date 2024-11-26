package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerImages;
import org.example.entity.enums.Status;
import org.example.repository.FlowerImagesRepository;
import org.example.service.IFlowerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlowerImageServiceImpl implements IFlowerImageService {
    @Autowired
    private FlowerImagesRepository flowerImagesRepository;

    @Override
    public List<FlowerImages> getAllImages() {
        return flowerImagesRepository.findAll();
    }

    @Override
    public FlowerImages getImageById(Integer id) {
        return flowerImagesRepository.findById(id).orElse(null);
    }

    @Override
    public FlowerImages createImage(FlowerImages image) {
        return flowerImagesRepository.save(image);
    }

    @Override
    public FlowerImages updateImage(Integer id, FlowerImages imageDetails) {
        Optional<FlowerImages> optionalImage = flowerImagesRepository.findById(id);
        if (optionalImage.isPresent()) {
            FlowerImages image = optionalImage.get();

            // Cập nhật URL của hình ảnh
            image.setImageURL(imageDetails.getImageURL());

            // Cập nhật sản phẩm, đảm bảo không null
            if (imageDetails.getFlower() != null) {
                image.setFlower(imageDetails.getFlower()); // Sử dụng setProduct
            }

            // Cập nhật trạng thái
            if (imageDetails.getStatus() != null) {
                image.setStatus(imageDetails.getStatus());
            }

            return flowerImagesRepository.save(image);
        } else {
            return null;
        }
    }


    @Override
    public void deleteImage(Integer id) {
        flowerImagesRepository.deleteById(id);
    }

    @Override
    public void disableImage(Integer id) {
        Optional<FlowerImages> optionalImage = flowerImagesRepository.findById(id);
        if (optionalImage.isPresent()) {
            FlowerImages image = optionalImage.get();
            image.setStatus(Status.DISABLE); // Chuyển trạng thái thành Disable
            flowerImagesRepository.save(image); // Lưu thay đổi
        }
    }

    @Override
    public List<FlowerImages> findImagesByProductID(int id) {
        return flowerImagesRepository.findFlowerImagesByFlowerFlowerIDAndStatus(id, Status.ENABLE);
    }
}
