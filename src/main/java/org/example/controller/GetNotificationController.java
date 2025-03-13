package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Notification;
import org.example.entity.enums.Notifi;
import org.example.entity.enums.Role;
import org.example.repository.NotificationRepository;
import org.example.service.INotificationService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class GetNotificationController {
    private final INotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final NotificationController notificationController;
    @GetMapping("")
    public ResponseEntity<?> getNotification() {
        List<Notification> notificationList = notificationService.findNotificationsByAccountid(getIDAccountFromAuthService.common());
        Map<String, Object> response = new HashMap<>();
        response.put("notificationList", notificationList);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @RequestMapping("open")
    public ResponseEntity<String> openNotificationList() {
        int accountId = getIDAccountFromAuthService.common();
        List<Notification> notificationList = notificationService.findNotificationsUnNotice(accountId);

        if (notificationList.isEmpty()) {
            return ResponseEntity.ok("Không có thông báo cần cập nhật.");
        }

        notificationList.forEach(notification -> notification.setNotice(Notifi.YES));
        notificationRepository.saveAll(notificationList);
        notificationController.notifyNotificationUpdate(getIDAccountFromAuthService.common());
        return ResponseEntity.ok("Cập nhật thông báo thành công!");
    }

    @RequestMapping("/link/{id}")
    public ResponseEntity<?> info(@PathVariable int id) {
        Notification notification = notificationService.findNotificationByNotificationID(id);
        Map<String, String> response = new HashMap<>();
        if (notification.getFlower() != null) {
            response.put("redirectUrl", "http://localhost:8000/detail/"+notification.getFlower().getFlowerID());
        } else if (notification.getOrder() != null) {
            response.put("redirectUrl", "http://localhost:8000/account/history/"+notification.getOrder().getOrderID());
        } else if (notification.getPreorder() != null) {
            response.put("redirectUrl", "http://localhost:8000/account/preorder/"+notification.getPreorder().getId());
        } else if (notification.getComment() != null) {
            response.put("redirectUrl", "http://localhost:8000/account/sendcomment/"+notification.getComment().getCommentID());
        } else if (notification.getBlog() != null) {
            response.put("redirectUrl", "http://localhost:8000/blog/"+notification.getBlog().getBlogid());
        } else if (notification.getBlogComment() != null) {
            response.put("redirectUrl", "http://localhost:8000/comment/"+notification.getBlogComment().getBlogcommentid());
        }
        notification.setSeen(Notifi.YES);
        notificationRepository.save(notification);
        notificationController.notifyNotificationUpdate(getIDAccountFromAuthService.common());
        return ResponseEntity.ok(response);
    }
}
