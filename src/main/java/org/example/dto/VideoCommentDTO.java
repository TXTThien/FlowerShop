package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.BlogComment;
import org.example.entity.BlogImage;
import org.example.entity.VideoComment;
import org.example.entity.VideoImage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoCommentDTO {
    private VideoComment videoComment;
    private Boolean likeComment;
    private List<VideoImage> videoImages;
    private List<VideoCommentDTO> childComment;
    private int numberCommentChild;
}
