package org.example.repository;

import org.example.entity.Blog;
import org.example.entity.BlogComment;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findBlogByBlogidAndStatus(int blogid, Status status);
    List<Blog> findBlogsByStatus(Status status);

    List<Blog> findBlogsByAccount_AccountID(int id);

    Blog findBlogsByBlogid(int blogid);

}
