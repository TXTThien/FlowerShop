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
public class FloOthCustomDTO {
    private Integer flowerID;
    private Integer otherID;
    private String flowerName;
    private String otherName;
    private BigDecimal price;
    private String type;
    private String status;
}
