package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Blog;
import org.example.entity.BlogFlower;
import org.example.entity.enums.Status;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlogDTO {
    private String content;
    private String title;
    private Status status;
    private List<String> imageurl;
    private List<BlogFlower> blogFlowers;
    private List<Integer> imageIDDelete;
}
