package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.BlogComment;
import org.example.repository.BlogCommentRepository;
import org.example.service.IBlogCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements IBlogCommentService {
    private final BlogCommentRepository blogCommentRepository;
    @Override
    public List<BlogComment> findBlogCommentsByBlogID(Integer blogid) {
        return blogCommentRepository.findBlogCommentsByBlogBlogid(blogid);
    }

    @Override
    public List<BlogComment> findCommentChild(Integer blogcommentid) {
        return blogCommentRepository.findBlogCommentsByFatherComment_Blogcommentid(blogcommentid);
    }

    @Override
    public BlogComment findBlogCommentByBlogCommentID(Integer commentid) {
        return blogCommentRepository.findBlogCommentByBlogcommentid(commentid);
    }
}
