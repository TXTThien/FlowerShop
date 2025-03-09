package org.example.service;

import org.example.entity.Blog;

import java.util.List;

public interface IBlogService {
    Blog findBlogByBlogID(Integer blogid);

    List<Blog> findAll();

    List<Blog> findStaffBlog(int accountid);

    Blog findBlogByBlogIDForStaff(int id);
}
