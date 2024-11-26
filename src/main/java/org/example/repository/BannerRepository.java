package org.example.repository;

import org.example.entity.Banner;
import org.example.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
    @Query("SELECT b FROM Banner b WHERE b.status = :status ORDER BY b.bannerID DESC")
    List<Banner> findTop4ByStatusOrderByIdDesc(@Param("status") Status status, Pageable pageable);
}
