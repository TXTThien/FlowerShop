package org.example.repository;

import org.example.entity.BlogComment;
import org.example.entity.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogImageRepository extends JpaRepository<BlogImage, Integer> {
    List<BlogImage> findBlogImagesByBlogComment_Blogcommentid(int id);
    List<BlogImage> findBlogImageByBlog_Blogid(int blogid);

    BlogImage findBlogImageByImageblogid(int id);
}
