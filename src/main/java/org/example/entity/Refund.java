package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Refund")
@Table(name = "refund", schema = "flowershop")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refundid", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`order`", nullable = false)
    private Order orderID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preorder", nullable = false)
    private Preorder preorderID;

    @JoinColumn(name = "bank",nullable = false)
    private String bank;

    @JoinColumn(name = "number",nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
