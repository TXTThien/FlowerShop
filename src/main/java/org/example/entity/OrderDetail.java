package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties("orderID")
@Entity(name = "OrderDetail")
@Table(name = "orderdetail", schema = "flowershop")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderdetailID", nullable = false)
    private Integer orderdetailID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false)
    private Order orderID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_sizeid", nullable = false)
    private FlowerSize flowerSize;


    @Column(name = "Quantity", nullable = false)
    private Integer quantity;


    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}
