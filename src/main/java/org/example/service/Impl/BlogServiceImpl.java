package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Blog;
import org.example.entity.enums.Status;
import org.example.repository.BlogRepository;
import org.example.service.IBlogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements IBlogService {
    private final BlogRepository blogRepository;
    @Override
    public Blog findBlogByBlogID(Integer blogid) {
        return blogRepository.findBlogByBlogidAndStatus(blogid, Status.ENABLE);
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findBlogsByStatus(Status.ENABLE);
    }

    @Override
    public List<Blog> findStaffBlog(int id) {
        return blogRepository.findBlogsByAccount_AccountID(id);
    }

    @Override
    public Blog findBlogByBlogIDForStaff(int id) {
        return blogRepository.findBlogsByBlogid(id);
    }


}
