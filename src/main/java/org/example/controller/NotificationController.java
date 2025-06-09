package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.*;
import org.example.repository.NotificationRepository;
import org.example.service.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;
    private final IWishlistService wishlistService;
    private final INotificationService notificationService;
    private final IFlowerService flowerService;
    private final IOrderService orderService;
    private final IPreOrderService preOrderService;
    private final ICommentService commentService;
    private final IRepCommentService repCommentService;
    private final IBlogCommentService blogCommentService;
    private final NotificationRepository notificationRepository;
    private final IAccountService accountService;
    private final IBlogService blogService;
    private final IBlogInteractService blogInteractService;
    private final EmailController emailController;
    private final IOrderDelivery orderDelivery;
    private final IVideoService videoService;
    private final IVideoCommentService videoCommentService;
    public void notifyNotificationUpdate(int accountId) {
        Map<String, Object> message = new HashMap<>();
        message.put("accountId", accountId);
        message.put("notificationCount", notificationCount(accountId));
        messagingTemplate.convertAndSend("/topic/notifi-update", message);
    }

    public int notificationCount(int accountid){
        List<Notification> notificationList = notificationService.findNotificationsByAccountid(accountid);
        return notificationList.size();
    }

    public void flowerRestockNotification(int flowerid) {
        List<Wishlist> wishlistList = wishlistService.findWishlistsByFlowerID(flowerid);

        if (wishlistList == null || wishlistList.isEmpty()) {
            return;
        }

        Flower flower = flowerService.findFlowerByIdEnable(flowerid);
        if (flower == null) {
            return;
        }

        List<Account> accountList = wishlistList.stream()
                .map(Wishlist::getAccountID)
                .filter(Objects::nonNull)
                .toList();

        if (accountList.isEmpty()) {
            return;
        }

        for (Account account : accountList) {
            try {
                Notification notification = new Notification();

                notification.setAccount(account);
                notification.setFlower(flower);
                notification.setStatus(Status.ENABLE);
                notification.setSeen(Notifi.NO);
                notification.setNotice(Notifi.NO);
                notification.setTime(LocalDateTime.now());
                notification.setText("Hoa " + flower.getName() + " mà bạn yêu thích vừa được cập nhật!");

                notificationRepository.save(notification);
                notifyNotificationUpdate(account.getAccountID());
            } catch (Exception e) {
                System.err.println("Lỗi khi lưu thông báo cho tài khoản " + account.getAccountID() + ": " + e.getMessage());
            }
        }
    }

    public void flowerPreorderNotification(int flowerid) {
        List<Wishlist> wishlistList = wishlistService.findWishlistsByFlowerID(flowerid);

        if (wishlistList == null || wishlistList.isEmpty()) {
            return;
        }

        Flower flower = flowerService.findFlowerByIdEnable(flowerid);
        if (flower == null) {
            return;
        }

        List<Account> accountList = wishlistList.stream()
                .map(Wishlist::getAccountID)
                .filter(Objects::nonNull)
                .toList();

        if (accountList.isEmpty()) {
            return;
        }

        for (Account account : accountList) {
            try {
                Notification notification = new Notification();

                notification.setAccount(account);
                notification.setFlower(flower);
                notification.setStatus(Status.ENABLE);
                notification.setSeen(Notifi.NO);
                notification.setNotice(Notifi.NO);
                notification.setTime(LocalDateTime.now());
                notification.setText("Bạn đã có thể đặt trước hoa " + flower.getName() + " mà bạn yêu thích");

                notificationRepository.save(notification);
                notifyNotificationUpdate(account.getAccountID());
            } catch (Exception e) {
                System.err.println("Lỗi khi lưu thông báo cho tài khoản " + account.getAccountID() + ": " + e.getMessage());
            }
        }
    }

    public void orderConditionNotification(int orderid) {
        Notification notification = new Notification();
        Order order = orderService.findOrderByOrderID(orderid);
        notification.setOrder(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        if (order.getCondition() == Condition.Pending)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã đặt thành công.");
        else if (order.getCondition() == Condition.Processing)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã được xác nhận");
        else if (order.getCondition() == Condition.Prepare)
            notification.setText("Shop đang chuẩn bị hàng cho đơn " + order.getOrderID());
        else if (order.getCondition() == Condition.Shipper_Delivering)
            notification.setText("Đơn hàng " + order.getOrderID() + " đang được shipper giao, hãy chú ý điện thoại");
        else if (order.getCondition() == Condition.First_Attempt_Failed)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã giao thất bại lần 1, còn 3 lần giao");
        else if (order.getCondition() == Condition.Second_Attempt_Failed)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã giao thất bại lần 2, còn 2 lần giao");
        else if (order.getCondition() == Condition.Third_Attempt_Failed)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã giao thất bại lần 2, còn 1 lần giao");
        else if (order.getCondition() == Condition.Return_to_shop)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã giao thất bại, trả về shop");
        else if (order.getCondition() == Condition.Delivered_Successfully)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã giao thành công, hãy xác nhận đơn hàng");
        else if (order.getCondition() == Condition.Cancelled && order.getPaid() == IsPaid.Yes)
            notification.setText("Đơn hàng " + order.getOrderID() + " đã được hoàn tiền thành công");
        else if (order.getCondition() == Condition.Cancelled)
            notification.setText("Yêu cầu hủy đơn hàng " + order.getOrderID() + " đã được chấp nhận, đơn đã bị hủy");
        else if (order.getCondition() == Condition.Refund)
            notification.setText("Yêu cầu hủy đơn hàng " + order.getOrderID() + " đã được chấp nhận, bạn có thể gửi yêu cầu hoàn tiền");
        notificationRepository.save(notification);
        String text ="\n"+ notification.getText() + "Hãy đến xem ngay: http://localhost:8000/account/history/"+order.getOrderID();
        emailController.OrderCondition(order,text);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void orderInfomationNotification(int orderid) {
        Notification notification = new Notification();
        Order order = orderService.findOrderByOrderID(orderid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndPreorderID(order.getAccountID().getAccountID(),order.getOrderID(),"TThông tin của đơn hàng ");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setOrder(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Thông tin của đơn hàng " + order.getOrderID() + " đã được cập nhật");
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void orderCancelFailNotification(int orderid) {
        Notification notification = new Notification();
        Order order = orderService.findOrderByOrderID(orderid);
        notification.setOrder(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Yêu cầu hủy đơn hàng: " + order.getOrderID() + " không được chấp nhận vì vi phạm quy định");
        notificationRepository.save(notification);
        String text ="\n"+ notification.getText() + "Hãy đến xem ngay: http://localhost:8000/account/history/"+order.getOrderID();
        emailController.OrderCancelFail(order,text);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void preOrderConditionNotification(int orderid) {
        Notification notification = new Notification();
        Preorder preorder = preOrderService.findPreorderByPreorderID(orderid);
        notification.setPreorder(preorder);
        notification.setAccount(preorder.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setNotice(Notifi.NO);
        if (preorder.getPrecondition() == Precondition.Waiting)
            notification.setText("Bạn có thể huy đơn đặt trước " + preorder.getId() + " nếu cần.");
        else if (preorder.getPrecondition() == Precondition.Ordering)
            notification.setText("Đơn đặt trước " + preorder.getId() + " đang chờ nhập hàng");
        else if (preorder.getPrecondition() == Precondition.Refund)
            notification.setText("Yêu cầu hủy cho đơn đặt trước " + preorder.getId() + " đã được chấp nhận, bạn có thể gửi yêu cầu hoàn tiền");
        else if (preorder.getPrecondition() == Precondition.Cancel)
            notification.setText("Yêu cầu hủy cho đơn đặt trước " + preorder.getId() + " đã được chấp nhận");
        else if (preorder.getPrecondition() == Precondition.Success)
            notification.setText("Đơn đặt trước " + preorder.getId() + " đã chuẩn bị xong, shop đã tạo đơn hàng gửi đến bạn, hãy kiểm tra lại thông tin");
        notificationRepository.save(notification);
        String text ="\n"+ notification.getText() + "Hãy đến xem ngay: http://localhost:8000/account/preorder/"+preorder.getId();
        emailController.PreOrderCondition(preorder,text);
        notifyNotificationUpdate(preorder.getAccount().getAccountID());
    }
    public void preOrderInfomationNotification(int orderid) {
        Notification notification = new Notification();
        Preorder preorder = preOrderService.findPreorderByPreorderID(orderid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndPreorderID(preorder.getAccount().getAccountID(),preorder.getId(),"Thông tin của đơn đặt trước ");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setPreorder(preorder);
        notification.setAccount(preorder.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Thông tin của đơn đặt trước " + preorder.getId() + " đã được cập nhật");
        notificationRepository.save(notification);
        notifyNotificationUpdate(preorder.getAccount().getAccountID());
    }
    public void preOrderCreateNotification(int orderid) {
        Notification notification = new Notification();
        Preorder preorder = preOrderService.findPreorderByPreorderID(orderid);
        notification.setPreorder(preorder);
        notification.setAccount(preorder.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Đơn đặt trước " + preorder.getId() + " đã được đặt thành công");
        notificationRepository.save(notification);
        notifyNotificationUpdate(preorder.getAccount().getAccountID());
    }

    public void preOrderSuccessNotification(int preorderid, int orderid) {
        Notification notification = new Notification();
        Order order = orderService.findOrderByOrderID(orderid);
        notification.setOrder(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setNotice(Notifi.NO);
        notification.setText("Đơn đặt trước " + preorderid + " đã chuẩn bị xong, shop đã tạo đơn hàng gửi đến bạn, hãy kiểm tra lại thông tin");
        notificationRepository.save(notification);

        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }


    public void commentRepNotification(int repcomment) {
        Notification notification = new Notification();
        RepComment repComment = repCommentService.findRepCommentByRepCommentID(repcomment);
        Comment comment = repComment.getComment();

        Notification existNotification = notificationService.findNotificationsByAccountidAndCommentID(comment.getAccountID().getAccountID(),comment.getCommentID(),"Góp ý của bạn đã được nhân viên ");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setComment(comment);
        notification.setAccount(comment.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Góp ý của bạn đã được nhân viên " +repComment.getAccount().getName()+ " phản hồi");
        notificationRepository.save(notification);

        notifyNotificationUpdate(comment.getAccountID().getAccountID());
    }

    public void commentCompleteNotification(int commentid) {
        Notification notification = new Notification();
        Comment comment = commentService.findCommentByID(commentid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndCommentID(comment.getAccountID().getAccountID(),comment.getCommentID(),"Góp ý của bạn đã được hoàn thành");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setTime(LocalDateTime.now());
        notification.setComment(comment);
        notification.setAccount(comment.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Góp ý của bạn đã được hoàn thành");
        notificationRepository.save(notification);
        notifyNotificationUpdate(comment.getAccountID().getAccountID());
    }

    public void commentRepForStaffNotification(int commentid) {
        Notification notification = new Notification();
        Comment comment = commentService.findCommentByID(commentid);
        Account account = repCommentService.findStaffRepAccount(commentid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndCommentID(account.getAccountID(),comment.getCommentID()," vừa trả lời phản hồi của bạn");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setTime(LocalDateTime.now());
        notification.setComment(comment);
        notification.setAccount(account);
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Khách hàng "+comment.getAccountID().getName()+" vừa trả lời phản hồi của bạn");
        notificationRepository.save(notification);
        notifyNotificationUpdate(account.getAccountID());
    }
    public void commentCompleteForStaffNotification(int commentid) {
        Notification notification = new Notification();
        Comment comment = commentService.findCommentByID(commentid);
        Account account = repCommentService.findStaffRepAccount(commentid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndCommentID(account.getAccountID(),comment.getCommentID()," đã xác nhận hoàn tất góp ý của họ");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setTime(LocalDateTime.now());
        notification.setComment(comment);
        notification.setAccount(account);
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Khách hàng "+comment.getAccountID().getName()+" đã xác nhận hoàn tất góp ý của họ");
        notificationRepository.save(notification);
        notifyNotificationUpdate(account.getAccountID());
    }
    public void commentCreateForStaffNotification(int commentid) {
        Comment comment = commentService.findCommentByID(commentid);

        List<Notification> notificationList = new ArrayList<>();
        List<Account> accountList = accountService.getAccountByRole(Role.staff);

        for (Account account : accountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setComment(comment);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Một góp ý vừa được tạo bởi khách hàng "+comment.getAccountID().getName());
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }

    }
    public void blogCommentLikeNotification(int blogcommentid) {
        Notification notification = new Notification();
        BlogComment blogComment = blogCommentService.findBlogCommentByBlogCommentID(blogcommentid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndBlogcommentID(blogComment.getAccount().getAccountID(),blogcommentid," người thích bình luận của bạn");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        if (blogComment.getFatherComment()!=null)
        {
            notification.setBlogComment(blogComment.getFatherComment());
        }
        else
        {
            notification.setBlogComment(blogComment);
        }
        notification.setTime(LocalDateTime.now());
        notification.setAccount(blogComment.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Đã có "+blogComment.getLike()+" người thích bình luận của bạn");
        notificationRepository.save(notification);
        notifyNotificationUpdate(blogComment.getAccount().getAccountID());
    }
    public void blogCommentRepNotification(int blogcommentid, Account account) {
        Notification notification = new Notification();
        BlogComment blogComment = blogCommentService.findBlogCommentByBlogCommentID(blogcommentid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndBlogcommentID(blogComment.getAccount().getAccountID(),blogcommentid," đã trả lời bình luận của bạn");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        if (blogComment.getFatherComment()!=null)
        {
            notification.setBlogComment(blogComment.getFatherComment());
        }
        else
        {
            notification.setBlogComment(blogComment);
        }
        notification.setTime(LocalDateTime.now());
        notification.setAccount(blogComment.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText(account.getName()+" đã trả lời bình luận của bạn");
        notificationRepository.save(notification);
        notifyNotificationUpdate(blogComment.getAccount().getAccountID());
    }

    public void blogLikeForStaffNotification(int blogid) {
        Notification notification = new Notification();
        Blog blog = blogService.findBlogByBlogID(blogid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndBlogID(blog.getAccount().getAccountID(),blogid," người thích bài viết của bạn");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setBlog(blog);
        notification.setTime(LocalDateTime.now());
        notification.setAccount(blog.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Đã có "+blog.getLike()+" người thích bài viết của bạn");
        notificationRepository.save(notification);
        notifyNotificationUpdate(blog.getAccount().getAccountID());
    }

    public void blogCommentForStaffNotification(int blogcommentid) {
        Notification notification = new Notification();
        BlogComment blogComment = blogCommentService.findBlogCommentByBlogCommentID(blogcommentid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndBlogcommentID(blogComment.getBlog().getAccount().getAccountID(),blogcommentid," đã bình luận trong bài viết của bạn");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setBlogComment(blogComment);
        notification.setTime(LocalDateTime.now());
        notification.setAccount(blogComment.getBlog().getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Người dùng "+blogComment.getAccount().getName()+" đã bình luận trong bài viết của bạn");
        notificationRepository.save(notification);
        notifyNotificationUpdate(blogComment.getBlog().getAccount().getAccountID());
    }

    public void blogPinForStaffNotification(int blogid) {
        Notification notification = new Notification();
        Blog blog = blogService.findBlogByBlogID(blogid);
        int countPin = blogInteractService.countPinBlog(blogid);
        Notification existNotification = notificationService.findNotificationsByAccountidAndBlogID(blog.getAccount().getAccountID(),blogid," người ghim bài viết của bạn");
        if (existNotification != null)
        {
            notification = existNotification;
        }
        notification.setBlog(blog);
        notification.setTime(LocalDateTime.now());
        notification.setAccount(blog.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setText("Đã có "+countPin+" người ghim bài viết của bạn");
        notificationRepository.save(notification);
        notifyNotificationUpdate(blog.getAccount().getAccountID());
    }
    public void cancelOrderRequestForStaffNotification(int orderid) {
        Order order = orderService.findOrderByOrderID(orderid);

        List<Notification> notificationList = new ArrayList<>();
        List<Account> staffAccountList = accountService.getAccountByRole(Role.staff);
        List<Account> adminAccountList = accountService.getAccountByRole(Role.admin);

        for (Account account : staffAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrder(order);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hủy đơn hàng cho đơn "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
        for (Account account : adminAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrder(order);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hủy đơn hàng cho đơn "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
    }
    public void refundOrderRequestForStaffNotification(int orderid) {
        Order order = orderService.findOrderByOrderID(orderid);

        List<Notification> notificationList = new ArrayList<>();
        List<Account> staffAccountList = accountService.getAccountByRole(Role.staff);
        List<Account> adminAccountList = accountService.getAccountByRole(Role.admin);

        for (Account account : staffAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrder(order);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hoàn tiền đơn hàng "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
        for (Account account : adminAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrder(order);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hoàn tiền đơn hàng "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
    }

    public void refundPreOrderRequestForStaffNotification(int orderid) {
        Order order = orderService.findOrderByOrderID(orderid);

        List<Account> staffAccountList = accountService.getAccountByRole(Role.staff);
        List<Account> adminAccountList = accountService.getAccountByRole(Role.admin);

        for (Account account : staffAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrder(order);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hoàn tiền đơn đặt trước "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
        for (Account account : adminAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrder(order);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hoàn tiền đơn đặt trước "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
    }

    public void OrDeNotificationCreate(int orderdelivery) {
        Notification notification = new Notification();
        OrderDelivery orderDelivery1 = orderDelivery.findOrderDelivery(orderdelivery);
        notification.setOrderDelivery(orderDelivery1);
        notification.setAccount(orderDelivery1.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Đơn hẹn giao hàng " + orderdelivery + " đã đặt thành công.");
        notificationRepository.save(notification);
        notifyNotificationUpdate(orderDelivery1.getAccountID().getAccountID());

        List<Account> staffAccountList = accountService.getAccountByRole(Role.staff);
        List<Account> adminAccountList = accountService.getAccountByRole(Role.admin);
        for (Account account: staffAccountList)
        {
            Notification notification1 = new Notification();
            notification1.setOrderDelivery(orderDelivery1);
            notification1.setAccount(account);
            notification1.setStatus(Status.ENABLE);
            notification1.setSeen(Notifi.NO);
            notification1.setNotice(Notifi.NO);
            notification1.setTime(LocalDateTime.now());
            notification1.setText("Một khách hàng vừa đặt đơn cố định.");
            notificationRepository.save(notification1);
            notifyNotificationUpdate(account.getAccountID());
        }
        for (Account account: adminAccountList)
        {
            Notification notification1 = new Notification();
            notification1.setOrderDelivery(orderDelivery1);
            notification1.setAccount(account);
            notification1.setStatus(Status.ENABLE);
            notification1.setSeen(Notifi.NO);
            notification1.setNotice(Notifi.NO);
            notification1.setTime(LocalDateTime.now());
            notification1.setText("Một khách hàng vừa đặt đơn cố định.");
            notificationRepository.save(notification1);
            notifyNotificationUpdate(account.getAccountID());
        }
    }
    public void refundCancelRequestForStaffNotification(int orderid) {
        OrderDelivery orderDelivery1 = orderDelivery.findOrderDelivery(orderid);

        List<Account> staffAccountList = accountService.getAccountByRole(Role.staff);
        List<Account> adminAccountList = accountService.getAccountByRole(Role.admin);

        for (Account account : staffAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrderDelivery(orderDelivery1);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hủy đơn cố định số: "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
        for (Account account : adminAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrderDelivery(orderDelivery1);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hủy đơn cố định số: "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
    }
    public void refundOrDeRequestForStaffNotification(int orderid) {
        OrderDelivery orderDelivery1 = orderDelivery.findOrderDelivery(orderid);

        List<Account> staffAccountList = accountService.getAccountByRole(Role.staff);
        List<Account> adminAccountList = accountService.getAccountByRole(Role.admin);

        for (Account account : staffAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrderDelivery(orderDelivery1);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hoàn tiền đơn đặt hàng số "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
        for (Account account : adminAccountList)
        {
            Notification notification = new Notification();
            notification.setTime(LocalDateTime.now());
            notification.setOrderDelivery(orderDelivery1);
            notification.setAccount(account);
            notification.setStatus(Status.ENABLE);
            notification.setSeen(Notifi.NO);
            notification.setNotice(Notifi.NO);
            notification.setText("Khách hàng vừa gửi yêu cầu hoàn tiền đơn đặt trước "+orderid);
            notificationRepository.save(notification);
            notifyNotificationUpdate(account.getAccountID());
        }
    }

    public void OrDeSuccessNotification(int ordeid, Order order) {
        Notification notification = new Notification();
        notification.setOrder(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setNotice(Notifi.NO);
        notification.setText("Đơn đặt giao hàng " + ordeid + " đã chuẩn bị xong, shop đã tạo đơn hàng gửi đến bạn, hãy kiểm tra lại thông tin");
        notificationRepository.save(notification);

        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void OrDeCancelFailNotification(int ordeid) {
        Notification notification = new Notification();
        OrderDelivery order = orderDelivery.findOrderDelivery(ordeid);
        notification.setOrderDelivery(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Yêu cầu hủy đơn đặt giao hàng: " + order.getId() + " không được chấp nhận vì vi phạm quy định");
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void OrDeCancelSuccessNotification(int ordeid) {
        Notification notification = new Notification();
        OrderDelivery order = orderDelivery.findOrderDelivery(ordeid);
        notification.setOrderDelivery(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Yêu cầu hủy đơn đặt giao hàng: " + order.getId() + " đã được chấp nhận, bạn có thể gửi yêu cầu hoàn tiền để nhận lại số tiền được thông báo");
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void NewOrDeAcceptNotification(int ordeid) {
        Notification notification = new Notification();
        OrderDelivery order = orderDelivery.findOrderDelivery(ordeid);
        notification.setOrderDelivery(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Đơn đặt giao hàng của bạn đã được chấp nhận: " + order.getId());
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void NewOrDeDeclineNotification(int ordeid) {
        Notification notification = new Notification();
        OrderDelivery order = orderDelivery.findOrderDelivery(ordeid);
        notification.setOrderDelivery(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Đơn đặt giao hàng của bạn đã bị từ chối. Hãy điền form yêu cầu hoàn tiền.");
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void refundPreorder(int id)
    {
        Notification notification = new Notification();
        Preorder order = preOrderService.findPreorderByPreorderID(id);
        notification.setPreorder(order);
        notification.setAccount(order.getAccount());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Đơn đặt trước đã được hoàn tiền thành công");
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccount().getAccountID());
    }

    public void refundOrDe(int id)
    {
        Notification notification = new Notification();
        OrderDelivery order = orderDelivery.findOrderDelivery(id);
        notification.setOrderDelivery(order);
        notification.setAccount(order.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Đơn đặt giao hàng đã được hoàn tiền thành công");
        notificationRepository.save(notification);
        notifyNotificationUpdate(order.getAccountID().getAccountID());
    }

    public void FlowerShortNotifiLike (int shortID) {
        Notification notification = new Notification();
        Video video = videoService.findVideoByIDEnable(shortID);
        notification.setVideo(video);
        notification.setAccount(video.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Một người dùng vừa thả tim cho FlowerShort của bạn");
        notificationRepository.save(notification);

        notifyNotificationUpdate(video.getAccountID().getAccountID());

    }

    public void FlowerShortNotifiComment (int videoid) {
        Notification notification = new Notification();
        Video video = videoService.findVideoByIDEnable(videoid);
        notification.setVideo(video);
        notification.setAccount(video.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Một người dùng vừa bình luận cho FlowerShort của bạn");
        notificationRepository.save(notification);

        notifyNotificationUpdate(video.getAccountID().getAccountID());
    }

    public void FlowerShortNotifiRepComment (int commentid) {
        Notification notification = new Notification();
        VideoComment videoComment = videoCommentService.findVidCommentByIDEnable(commentid);
        notification.setVideo(videoComment.getVideo());
        notification.setAccount(videoComment.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Một người dùng vừa trả lời cho bình luận của bạn");
        notificationRepository.save(notification);

        notifyNotificationUpdate(videoComment.getAccountID().getAccountID());
    }

    public void FlowerShortNotifiLikeComment (int commentid) {
        Notification notification = new Notification();
        VideoComment videoComment = videoCommentService.findVidCommentByIDEnable(commentid);
        notification.setVideo(videoComment.getVideo());
        notification.setAccount(videoComment.getAccountID());
        notification.setStatus(Status.ENABLE);
        notification.setSeen(Notifi.NO);
        notification.setNotice(Notifi.NO);
        notification.setTime(LocalDateTime.now());
        notification.setText("Một người dùng vừa thả tim cho bình luận của bạn");
        notificationRepository.save(notification);

        notifyNotificationUpdate(videoComment.getAccountID().getAccountID());
    }
}
