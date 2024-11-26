package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CommentImage")
@Table(name = "commentimage", schema = "flowershop")
public class CommentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageID", nullable = false)
    private Integer imageID;

    @Column(name = "ImageURL", length = 1000)
    private String url;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CommentID")
    private Comment comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RepcommentID")
    private RepComment repComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}
