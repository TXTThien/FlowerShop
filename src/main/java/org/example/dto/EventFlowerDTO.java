package org.example.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Event;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventFlowerDTO {

    private Integer  idEventFlower;
    private String flowerName;
    private String imageurl;
    private List<FlowerSizeDTO> size;
    private String sizeChoose;
    private Integer flowerID;
    private Integer sizeIDChoose;
    private BigDecimal saleOff;
}
