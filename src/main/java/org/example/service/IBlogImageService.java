package org.example.service;

import org.example.entity.BlogImage;

import java.util.List;

public interface IBlogImageService {
    List<BlogImage> findBlogImagesByCommentID(Integer blogcommentid);

    List<BlogImage> findBlogImagesByBlogID(Integer blogid);

    BlogImage findBlogImageByBlogImageID(int id);
}
