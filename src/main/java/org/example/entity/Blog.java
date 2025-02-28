package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Blog")
@Table(name = "blog", schema = "flowershop")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blogid", nullable = false)
    private Integer blogid;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "`like`")
    private BigInteger like;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid", nullable = false)
    private Account account;
}
