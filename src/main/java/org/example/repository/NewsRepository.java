package org.example.repository;

import org.example.entity.News;
import org.example.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    @Query("SELECT n FROM News n WHERE n.status = :status ORDER BY n.newsID DESC")
    List<News> findTop3ByStatusOrderByIdDesc(@Param("status") Status status, Pageable pageable);

    List<News> findNewsByStatusOrderByDateDesc(Status status);
    News findNewsByNewsIDAndStatus(int id, Status status);
}
