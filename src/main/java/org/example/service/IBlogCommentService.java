package org.example.service;

import org.example.entity.BlogComment;

import java.util.List;

public interface IBlogCommentService {
    List<BlogComment> findBlogCommentsByBlogID(Integer blogid);

    List<BlogComment> findCommentChild(Integer blogcommentid);

    BlogComment findBlogCommentByBlogCommentID(Integer commentid);
}
