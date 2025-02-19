package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "BlogInteract")
@Table(name = "bloginteract", schema = "flowershop")
public class BlogInteract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bloginteractid", nullable = false)
    private Integer bloginteractid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogid")
    private Blog bloglike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private BlogComment blogComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogpin")
    private Blog blogpin;
}
