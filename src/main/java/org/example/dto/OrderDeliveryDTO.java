package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryDTO {
    private int orderDeliveryTypeID;
    private String deliverper;
    private List<FlowerChoose> flowerChooses;
    private String name;
    private String note;
    private String phone;
    private String address;
    private LocalDateTime dateStart;
}
