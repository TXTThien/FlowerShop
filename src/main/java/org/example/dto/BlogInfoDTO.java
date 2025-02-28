package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Blog;
import org.example.entity.BlogComment;
import org.example.entity.BlogFlower;
import org.example.entity.BlogImage;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoDTO {
    private Blog blog;
    private List<BlogCommentDTO> blogCommentDTOS;
    private List<BlogImage> blogImages;
    private List<ProductDTO> blogFlower;
    private Boolean likeBlog;
    private Boolean pinBlog;
    private int numberComments;
}
