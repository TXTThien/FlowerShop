package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippingDTO {
    private int OrderID;
    private Integer  ShippingID;
    private Integer  ShipperID;

    private String ShipperName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime shipStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime shipEnd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private IsPaid isPaid;
    private BigDecimal Total;
    private String Address;
    private String Phone;
    private String Name;
    private Condition condition;
    private String Note;
    private String[] FlowerName;
    private String[] SizeName;
    private BigDecimal[] Price;
    private Integer[] Quantity;
    private Float[] Length;
    private Float[] Height;
    private Float[] Width;
    private Float[] Weight;
    private String ShipperNote;
}
