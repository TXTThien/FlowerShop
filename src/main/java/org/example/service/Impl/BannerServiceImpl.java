package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Banner;
import org.example.entity.enums.Status;
import org.example.repository.BannerRepository;
import org.example.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner findById(Integer id) {
        return bannerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner update(Banner banner) {
        return bannerRepository.findById(banner.getBannerID())
                .map(existingBanner -> {
                    // Cập nhật các thuộc tính của banner
                    existingBanner.setImage(banner.getImage());
                    existingBanner.setFlower(banner.getFlower());
                    existingBanner.setCategory(banner.getCategory());
                    existingBanner.setPurpose(banner.getPurpose());
                    existingBanner.setNews(banner.getNews());
                    existingBanner.setStatus(banner.getStatus());
                    return bannerRepository.save(existingBanner);
                }).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return bannerRepository.findById(id).map(existingBanner -> {
            existingBanner.setStatus(Status.DISABLE);
            bannerRepository.save(existingBanner);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Banner> find4BannerEnable() {
        Pageable pageable = PageRequest.of(0, 4);
        List<Banner> banners = bannerRepository.findTop4ByStatusOrderByIdDesc(Status.ENABLE, pageable);
        return banners;
    }
}
