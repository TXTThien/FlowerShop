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
public class InfoOrderDelivery {
    private Integer id;
    private String address;
    private String phoneNumber;
    private String name;
    private String note;
    private String orDeType;
    private String days;
    private BigDecimal costperday;
    private LocalDateTime start;
    private LocalDateTime end;
    private BigDecimal total;
    protected Deliverper deliverper;
    protected OrDeCondition orDeCondition;
    private int numberDelivered;
    List<OrDeDetailDTO> orDeDetailDTOS;
    private boolean deliver;
}
