package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Banner;

import org.example.entity.Purpose;
import org.example.repository.CategoryRepository;
import org.example.repository.FlowerRepository;
import org.example.repository.NewsRepository;
import org.example.repository.PurposeRepository;
import org.example.service.IBannerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.enums.Status;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/banner")
@RequiredArgsConstructor
public class AdminBannerController {

    private final IBannerService bannerService;
    private final FlowerRepository flowerRepository;
    private final NewsRepository newsRepository;
    private final PurposeRepository purposeRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Banner> getAllBanners() {
        return bannerService.findAll();
    }
    @PostMapping
    public ResponseEntity<?> createBanner(@RequestBody Banner banner) {
        if (banner.getFlower() != null && banner.getFlower().getFlowerID() != null) {
            banner.setFlower(flowerRepository.findById(banner.getFlower().getFlowerID()).orElse(null));
        }

        if (banner.getNews() != null && banner.getNews().getNewsID() != null) {
            banner.setNews(newsRepository.findById(banner.getNews().getNewsID()).orElse(null));
        }

        if (banner.getCategory() != null && banner.getCategory().getCategoryID() != null) {
            banner.setCategory(categoryRepository.findById(banner.getCategory().getCategoryID()).orElse(null));
        }

        if (banner.getPurpose() != null && banner.getPurpose().getPurposeID() != null) {
            banner.setPurpose(purposeRepository.findById(banner.getPurpose().getPurposeID()).orElse(null));
        }
        Banner savedBanner = bannerService.save(banner);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBanner);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable Integer id) {
        Banner banner = bannerService.findById(id);
        if (banner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(banner);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateBanner(@PathVariable Integer id, @RequestBody Banner banner) {
        try {
            Banner existingBanner = bannerService.findById(id);
            if (existingBanner == null) {
                return ResponseEntity.notFound().build();
            }

            if (banner.getFlower() != null && banner.getFlower().getFlowerID() != null) {
                existingBanner.setFlower(flowerRepository.findById(banner.getFlower().getFlowerID()).orElse(null));
            }

            if (banner.getNews() != null && banner.getNews().getNewsID() != null) {
                existingBanner.setNews(newsRepository.findById(banner.getNews().getNewsID()).orElse(null));
            }

            if (banner.getCategory() != null && banner.getCategory().getCategoryID() != null) {
                existingBanner.setCategory(categoryRepository.findById(banner.getCategory().getCategoryID()).orElse(null));
            }

            if (banner.getPurpose() != null && banner.getPurpose().getPurposeID() != null) {
                existingBanner.setPurpose(purposeRepository.findById(banner.getPurpose().getPurposeID()).orElse(null));
            }

            existingBanner.setImage(banner.getImage());
            existingBanner.setStatus(banner.getStatus());

            Banner updatedBanner = bannerService.update(existingBanner);
            return ResponseEntity.ok(updatedBanner);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the banner: " + e.getMessage());
        }
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softdeleteBanner(@PathVariable Integer id) {
        Banner existingBanner = bannerService.findById(id);
        if (existingBanner == null) {
            return ResponseEntity.notFound().build();
        }
        // Thay đổi trạng thái của banner thành Disable
        existingBanner.setStatus(Status.DISABLE);
        bannerService.update(existingBanner);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Banner existingBanner = bannerService.findById(id);
        if (existingBanner == null) {
            return ResponseEntity.notFound().build();
        }
        bannerService.harddeleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
