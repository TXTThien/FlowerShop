package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRepCommentDTO {

    private Integer commentID;
    private String commentTitle;
    private String commentText;
    private String commentType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime commentDate;

    private String commentStatus;
    private String commentStative;
    private String image;

    private List<RepCommentDTO> repComments; // Danh sách các RepComment

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RepCommentDTO {

        private Integer repcommentID;
        private Integer accountID; // ID của tài khoản
        private String accountName; // Tên tài khoản (nếu cần)
        private String avatarRep;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime repcommentDate;

        private String repcommentText;
        private String repcommentStatus;
        private String image;
    }
}
