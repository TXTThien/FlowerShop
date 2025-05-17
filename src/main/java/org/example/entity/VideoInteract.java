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
@Entity(name = "VideoInteract")
@Table(name = "videointeract", schema = "flowershop")
public class VideoInteract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videointeractid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid",nullable = false)
    private Account accountID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vidlike",nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentlike",nullable = false)
    private VideoComment videoComment;
}
