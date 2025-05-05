package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftInfoDTOStaff {
    private Integer giftid;
    private String name;
    private String typegift;
    private BigDecimal discountpercent;
    private Integer flowerSizeid;
    private String description;
    private Status status;
    private BigDecimal percent;
    private Integer categoryid;
    private Integer purposeid;
    private Integer typeid;
    private LocalDateTime timeend;
}
