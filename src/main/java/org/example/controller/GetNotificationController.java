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
        } else if (notification.getOrder() != null && notification.getAccount().getRole()== Role.user) {
            response.put("redirectUrl", "http://localhost:8000/account/history/"+notification.getOrder().getOrderID());
        } else if (notification.getOrder() != null && notification.getAccount().getRole() == Role.staff && notification.getText().contains("Khách hàng vừa gửi yêu cầu hủy đơn hàng cho đơn ")) {
            response.put("redirectUrl", "http://localhost:8000/StaffCanceldelivery");
        } else if (notification.getOrder() != null && notification.getAccount().getRole() == Role.staff && (notification.getText().contains("Khách hàng vừa gửi yêu cầu hoàn tiền đơn hàng ") || notification.getText().contains("Khách hàng vừa gửi yêu cầu hoàn tiền đơn đặt trước "))) {
            response.put("redirectUrl", "http://localhost:8000/StaffRefund");
        } else if (notification.getOrder() != null && notification.getAccount().getRole() == Role.admin && notification.getText().contains("Khách hàng vừa gửi yêu cầu hủy đơn hàng cho đơn ")) {
            response.put("redirectUrl", "http://localhost:8000/AdminCanceldelivery");
        } else if (notification.getOrder() != null && notification.getAccount().getRole() == Role.admin && (notification.getText().contains("Khách hàng vừa gửi yêu cầu hoàn tiền đơn hàng ") || notification.getText().contains("Khách hàng vừa gửi yêu cầu hoàn tiền đơn đặt trước "))) {
            response.put("redirectUrl", "http://localhost:8000/AdminRefund");
        } else if (notification.getPreorder() != null) {
            response.put("redirectUrl", "http://localhost:8000/account/preorder/"+notification.getPreorder().getId());
        } else if (notification.getComment() != null && notification.getAccount().getRole() == Role.user) {
            response.put("redirectUrl", "http://localhost:8000/account/sendcomment/"+notification.getComment().getCommentID());
        } else if (notification.getComment() != null && notification.getText().contains("vừa trả lời phản hồi của bạn")) {
            response.put("redirectUrl", "http://localhost:8000/staffaccount/processingcomment/"+notification.getComment().getCommentID());
        } else if (notification.getComment() != null && notification.getText().contains("đã xác nhận hoàn tất góp ý của họ")) {
            response.put("redirectUrl", "http://localhost:8000/staffaccount/completecomment/"+notification.getComment().getCommentID());
        } else if (notification.getComment() != null && notification.getText().contains("Một góp ý vừa được tạo bởi khách hàng ")) {
            response.put("redirectUrl", "http://localhost:8000/staffaccount/comment/"+notification.getComment().getCommentID());

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
