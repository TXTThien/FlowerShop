package org.example.service;

import org.example.entity.BlogFlower;

import java.util.List;

public interface IBlogFlowerService {
    List<BlogFlower> findBlogFlowerByBlogID(Integer blogid);

    BlogFlower findBlogFlowerByBlogFlowerID(Integer integer);
}
