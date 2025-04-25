package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;
import org.example.entity.enums.Deliverper;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "OrderDeliveryType")
@Table(name = "`orderdeliverytype`", schema = "flowershop")
public class OrderDeliveryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliverytypeid", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "costperday", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "days", nullable = false)
    private String days;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    protected Status status;
}
