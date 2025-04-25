package org.example.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Account;
import org.example.entity.OrderDeliveryType;
import org.example.entity.enums.Deliverper;
import org.example.entity.enums.OrDeCondition;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
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
    private int  accountID;
    private int orderDeliveryID;
    private LocalDateTime dateEnd;
    private BigDecimal total;
    protected OrDeCondition orDeCondition;
    private int orderDeliType;
}
