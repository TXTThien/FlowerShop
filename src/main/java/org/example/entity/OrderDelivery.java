package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Deliverper;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.OrDeCondition;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "OrderDelivery")
@Table(name = "`orderdelivery`", schema = "flowershop")
public class OrderDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdeliveryid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid", nullable = false)
    private Account accountID;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phoneNumber",length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliverytypeid", nullable = false)
    private OrderDeliveryType orderDeliveryType;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected Status status;

    @Column(name = "total", nullable = false, precision = 50, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "deliverper")
    protected Deliverper deliverper;

    @Column(name = "vnp_transaction_no")
    private String vnp_TransactionNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    protected OrDeCondition orDeCondition;
}
