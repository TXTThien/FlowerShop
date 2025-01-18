package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Category;
import org.example.entity.Purpose;
import org.example.entity.enums.Preorderable;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlowerDTO {
    private int flowerID;
    private String name;
    private String description;
    private String image;
    private String languageOfFlowers;
    private Category category;
    private Purpose purpose;
    private BigDecimal price;
    private Preorderable preorderable;
}
