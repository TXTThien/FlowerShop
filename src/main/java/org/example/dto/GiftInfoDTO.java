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
public class GiftInfoDTO {
    private int id;
    private String name;
    private String typegift;
    private BigDecimal discountpercent;
    private FlowerSizeDTO flowerSizeDTO;
    private String description;
    private BigDecimal percent;
}
