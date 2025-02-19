package org.example.repository;

import org.example.entity.BlogComment;
import org.example.entity.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogImageRepository extends JpaRepository<BlogImage, Integer> {
}
