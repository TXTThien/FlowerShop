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
public class BlogFlowerDTO {
    private List<Integer> listFlower;
    private List<Integer> listBlogFlowerID;
}
