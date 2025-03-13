package org.example.repository;

import org.aspectj.weaver.ast.Not;
import org.example.entity.News;
import org.example.entity.Notification;
import org.example.entity.enums.Notifi;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findNotificationsByAccount_AccountIDAndStatusOrderByTimeDesc (int id, Status status);
    Notification findNotificationByAccount_AccountIDAndFlower_FlowerIDAndStatusAndTextContaining(int id, int flower, Status status, String text);
    Notification findNotificationByAccount_AccountIDAndOrder_OrderIDAndStatusAndTextContaining(int id, int order, Status status, String text);
    Notification findNotificationByAccount_AccountIDAndPreorder_IdAndStatusAndTextContaining(int id, int preorder, Status status, String text);
    Notification findNotificationByAccount_AccountIDAndComment_CommentIDAndStatusAndTextContaining(int id, int comment, Status status, String text);
    Notification findNotificationByAccount_AccountIDAndBlogComment_BlogcommentidAndStatusAndTextContaining(int id, int blogcomment, Status status, String text);
    Notification findNotificationByAccount_AccountIDAndBlog_BlogidAndStatusAndTextContaining(int id, int blog, Status status, String text);
    List<Notification> findNotificationsByAccount_AccountIDAndStatusAndNotice (int id, Status status, Notifi notifi);

    Notification findNotificationByIdAndStatus(int id, Status status);
}
