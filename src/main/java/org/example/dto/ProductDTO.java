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
public class ProductDTO {
    private Integer ProductID;
    private Integer flowerSizeID;
    private String avatar;
    private String title;
    private Integer sold;
    private BigDecimal price;
    private BigDecimal priceEvent = BigDecimal.ZERO;
    private BigDecimal saleOff = BigDecimal.ZERO;

    public ProductDTO(Integer ProductID, String avatar, String title, Integer sold, BigDecimal price, Integer flowerSizeID) {
        this.ProductID = ProductID;
        this.avatar = avatar;
        this.title = title;
        this.sold = sold;
        this.price = price;
        this.flowerSizeID  = flowerSizeID;
    }

}