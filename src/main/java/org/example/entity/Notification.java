package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Notifi;
import org.example.entity.enums.Status;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Notification")
@Table(name = "notification", schema = "flowershop")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "notice_text",length = 1000, nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flowerid")
    private Flower flower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preorderid")
    private Preorder preorder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogcommentid")
    private BlogComment blogComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogid")
    private Blog blog;

    @Enumerated(EnumType.STRING)
    @Column(name = "seen", nullable = false)
    protected Notifi seen;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice", nullable = false)
    protected Notifi notice;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Column(name ="time", nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shortid")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderdeliveryid", nullable = false)
    private OrderDelivery orderDelivery;
}
