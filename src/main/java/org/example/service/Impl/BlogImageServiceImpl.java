package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.BlogImage;
import org.example.repository.BlogImageRepository;
import org.example.service.IBlogImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogImageServiceImpl implements IBlogImageService {
    private final BlogImageRepository blogImageRepository;
    @Override
    public List<BlogImage> findBlogImagesByCommentID(Integer blogcommentid) {
        return blogImageRepository.findBlogImagesByBlogComment_Blogcommentid(blogcommentid);
    }

    @Override
    public List<BlogImage> findBlogImagesByBlogID(Integer blogid) {
        return blogImageRepository.findBlogImageByBlog_Blogid(blogid);
    }

    @Override
    public BlogImage findBlogImageByBlogImageID(int id) {
        return blogImageRepository.findBlogImageByImageblogid(id);
    }
}
