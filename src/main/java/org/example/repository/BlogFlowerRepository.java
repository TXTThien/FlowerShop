package org.example.repository;

import org.example.entity.BlogComment;
import org.example.entity.BlogFlower;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogFlowerRepository extends JpaRepository<BlogFlower, Integer> {
    List<BlogFlower> findBlogFlowersByBlog_Blogid(int blogid);
    BlogFlower findBlogFlowerByFlowerblogid(int id);
}
