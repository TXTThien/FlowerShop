package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Notification;
import org.example.entity.enums.Notifi;
import org.example.entity.enums.Status;
import org.example.repository.NotificationRepository;
import org.example.service.INotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification> findNotificationsByAccountid(int accountid) {
        return notificationRepository.findNotificationsByAccount_AccountIDAndStatusOrderByTimeDesc(accountid, Status.ENABLE);
    }

    @Override
    public Notification findNotificationsByAccountidAndFlowerID(int accountid, int flowerid, String text) {
        return notificationRepository.findNotificationByAccount_AccountIDAndFlower_FlowerIDAndStatusAndTextContaining(accountid,flowerid,Status.ENABLE,text);
    }

    @Override
    public Notification findNotificationsByAccountidAndOrderID(int accountid, int orderid, String text) {
        return notificationRepository.findNotificationByAccount_AccountIDAndOrder_OrderIDAndStatusAndTextContaining(accountid,orderid,Status.ENABLE,text);
    }

    @Override
    public Notification findNotificationsByAccountidAndPreorderID(int accountid, int preorderid, String text) {
        return notificationRepository.findNotificationByAccount_AccountIDAndPreorder_IdAndStatusAndTextContaining(accountid,preorderid,Status.ENABLE,text);
    }

    @Override
    public Notification findNotificationsByAccountidAndCommentID(int accountid, int commentid, String text) {
        return notificationRepository.findNotificationByAccount_AccountIDAndComment_CommentIDAndStatusAndTextContaining(accountid,commentid,Status.ENABLE,text);
    }

    @Override
    public Notification findNotificationsByAccountidAndBlogcommentID(int accountid, int blogcommentid, String text) {
        return notificationRepository.findNotificationByAccount_AccountIDAndBlogComment_BlogcommentidAndStatusAndTextContaining(accountid,blogcommentid,Status.ENABLE,text);
    }

    @Override
    public Notification findNotificationsByAccountidAndBlogID(int accountid, int blogid, String text) {
        return notificationRepository.findNotificationByAccount_AccountIDAndBlog_BlogidAndStatusAndTextContaining(accountid,blogid,Status.ENABLE,text);
    }

    @Override
    public List<Notification> findNotificationsUnNotice(int common) {
        return notificationRepository.findNotificationsByAccount_AccountIDAndStatusAndNotice(common, Status.ENABLE, Notifi.NO);
    }

    @Override
    public Notification findNotificationByNotificationID(int id) {
        return notificationRepository.findNotificationByIdAndStatus(id, Status.ENABLE);
    }


}
