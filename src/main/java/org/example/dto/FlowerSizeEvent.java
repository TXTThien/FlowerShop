package org.example.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Flower;
import org.example.entity.enums.Preorderable;
import org.example.entity.enums.Status;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlowerSizeEvent {
    private Integer flowerSizeID;
    private Flower flower;
    private String sizeName;
    private float length;
    private float high;
    private float width;
    private float weight;
    private Integer stock;
    private BigDecimal price;
    private Status status;
    private Preorderable preorderable;
    private BigDecimal priceEvent;
    private BigDecimal saleOff;
}
