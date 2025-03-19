package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Integer cartID;
    private Integer number;
    private Status status;
    private Integer productID;
    private String productTitle;
    private String avatar;
    private String sizeChoose;
    private BigDecimal productPrice;
    private BigDecimal productPriceEvent;
    private BigDecimal saleOff;
    private List<String> sizes;
    private List<Integer> stock;
    private Integer purposeID;
    private Integer categoryID;
    private String Type;
}