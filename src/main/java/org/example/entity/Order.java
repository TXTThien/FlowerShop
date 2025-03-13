package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Order")
@Table(name = "`order`", schema = "flowershop")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", nullable = false)
    private Integer orderID;

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

    @Column(name = "total_amount", nullable = false, precision = 50, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "Paid", precision = 50, scale = 2)
    private BigDecimal hadpaid;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "phone_number",length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Note")
    private String note;

    @Column(name = "vnp_TransactionNo")
    private String vnp_TransactionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShippingID")
    private Shipping shipping;

    @Enumerated(EnumType.STRING)
    @Column(name = "`Condition`", nullable = false)
    protected Condition condition;

    @OneToMany(mappedBy = "orderID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @Column(name = "picture",length = 10000)
    private String picture;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "confirm")
    protected IsPaid confirm;

    @Column(name = "time")
    private LocalDateTime time;
}
