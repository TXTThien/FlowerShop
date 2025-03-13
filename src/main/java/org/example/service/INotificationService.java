package org.example.service;

import org.aspectj.weaver.ast.Not;
import org.example.entity.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> findNotificationsByAccountid(int accountid);
    Notification findNotificationsByAccountidAndFlowerID(int accountid, int flowerid, String text);
    Notification findNotificationsByAccountidAndOrderID(int accountid, int orderid, String text);
    Notification findNotificationsByAccountidAndPreorderID(int accountid, int preorderid, String text);
    Notification findNotificationsByAccountidAndCommentID(int accountid, int commentid, String text);
    Notification findNotificationsByAccountidAndBlogcommentID(int accountid, int blogcommentid, String text);
    Notification findNotificationsByAccountidAndBlogID(int accountid, int blogid, String text);


    List<Notification> findNotificationsUnNotice(int common);

    Notification findNotificationByNotificationID(int id);
}
