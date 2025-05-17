package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Commentable;
import org.example.entity.enums.Status;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Video")
@Table(name = "video", schema = "flowershop")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid",nullable = false)
    private Account accountID;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "video_url", nullable = false, length = 1000)
    private String vid_url;

    @Column(name = "thumbnail_url", nullable = false, length = 1000)
    private String thumb_url;

    @Column(name = "views", nullable = false)
    private Long views;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "comments", nullable = false)
    private Long comments;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "commentable", nullable = false)
    protected Commentable commentable;

}
