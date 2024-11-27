package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Stative;
import org.example.entity.enums.Status;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Comment")
@Table(name = "comment", schema = "flowershop")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID", nullable = false)
    private Integer commentID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CommenttypeID")
    private CommentType commentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID")
    private Account accountID;

    @Column(name = "comment_title", length = 100, nullable = false)
    private String title;

    @Column(name = "comment_text", nullable = false,columnDefinition = "TEXT")
    private String text;

    @Column(name = "comment_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "Stative", nullable = false)
    protected Stative stative;

    @Column(name = "Image", length = 1000)
    private String image;
}
