package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.BlogComment;
import org.example.entity.BlogImage;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogCommentDTO {
    private BlogComment blogComment;
    private Boolean likeComment;
    private List<BlogImage> blogImages;
    private List<BlogCommentDTO> childComment;
    private int numberCommentChild;
}
