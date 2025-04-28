package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrDeDetailDTO {
    private int id;
    private int OrDeID;
    private int count;
    private String flowername;
    private String flowersize;
    private float height;
    private float weight;
    private float width;
    private float length;
    private BigDecimal price;
}
