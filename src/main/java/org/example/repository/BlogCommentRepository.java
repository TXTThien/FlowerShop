package org.example.repository;

import org.example.entity.Banner;
import org.example.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    List<BlogComment> findBlogCommentsByBlogBlogid(int id);
    List<BlogComment> findBlogCommentsByFatherComment_Blogcommentid(int id);
    BlogComment findBlogCommentByBlogcommentid(int id);
}
