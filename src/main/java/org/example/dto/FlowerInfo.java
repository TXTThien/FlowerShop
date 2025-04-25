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
public class FlowerInfo {
    private int id;
    List<FlowerSizeDTO> flowerSizeDTOS;
    private String image;
    private String name;
}
