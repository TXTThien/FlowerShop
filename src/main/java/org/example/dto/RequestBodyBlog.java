package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyBlog {
    private Integer blogid;
    private Integer commentid;
    private String comment;
    private List<String> imageurl;
}
