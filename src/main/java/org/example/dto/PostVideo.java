package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.enums.Commentable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostVideo {
    private String title;
    private String description;
    private String vid_url;
    private String thumb_url;
    protected Commentable commentable;
}
