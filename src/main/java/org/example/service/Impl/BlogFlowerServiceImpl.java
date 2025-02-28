package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.BlogFlower;
import org.example.entity.enums.Status;
import org.example.repository.BlogFlowerRepository;
import org.example.service.IBlogFlowerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogFlowerServiceImpl implements IBlogFlowerService {
    private final BlogFlowerRepository blogFlowerRepository;
    @Override
    public List<BlogFlower> findBlogFlowerByBlogID(Integer blogid) {
        return blogFlowerRepository.findBlogFlowersByBlog_Blogid(blogid);
    }

    @Override
    public BlogFlower findBlogFlowerByBlogFlowerID(Integer integer) {
        return blogFlowerRepository.findBlogFlowerByFlowerblogid(integer);
    }
}
