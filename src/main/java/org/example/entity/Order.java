package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"billInfoID","AccountID"})
@Entity(name = "Order")
@Table(name = "`order`", schema = "flowershop")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", nullable = false)
    private Integer orderID;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID")
    private Account accountID;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "Ispaid",nullable = false)
    protected IsPaid paid;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "phone_number",length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "Name",length = 255, nullable = false)
    private String name;

    @Column(name = "Note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShippingID", nullable = false)
    private Shipping shipping;

    @Enumerated(EnumType.STRING)
    @Column(name = "`Condition`", nullable = false)
    protected Condition condition;

    @OneToMany(mappedBy = "orderID")
    @ToString.Exclude
    private Set<OrderDetail> orderDetailSet = new LinkedHashSet<>();
}
