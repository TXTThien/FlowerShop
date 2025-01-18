package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Cancelenable;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Preorder")
@Table(name = "preorder", schema = "flowershop")
public class Preorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    private Account account;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "total_amount", nullable = false, precision = 50, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "phone_number",length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "Name",length = 255, nullable = false)
    private String name;

    @Column(name = "Note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "Cancelenable", nullable = false)
    protected Cancelenable cancelenable;

}
