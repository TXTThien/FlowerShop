package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.auth.ForgotPasswordRequest;
import org.example.entity.*;
import org.example.entity.enums.IsPaid;
import org.example.service.IAccountService;
import org.example.service.IPreOrderService;
import org.example.service.IPreorderdetailService;
import org.example.service.Impl.EmailServiceImpl;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailServiceImpl emailService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final IPreorderdetailService preorderdetailService;

    public void BuySuccess(Order order, int idAccount) {
        Account account = accountService.getAccountById(idAccount);
        String email = account.getEmail();
        String subject = "Thông báo đặt hàng thành công";
        String text;
        if (order.getPaid() == IsPaid.Yes) {
            text = "Bạn đã thanh toán thành công, mã số đơn hàng: " + order.getOrderID() + "\n" +
                    "Mã thanh toán: " + order.getVnp_TransactionNo() + "\n" +
                    "Số tiền đã thanh toán: " + order.getHadpaid() + "\n" +
                    "Giá trị đơn hàng: " + order.getTotalAmount() + "\n" +
                    "Hãy lưu giữ lại mã thanh toán và mã số đơn hàng nếu có khiếu nại hoặc yêu cầu hoàn trả tiền.";
        } else if (order.getPaid() == IsPaid.No && order.getHadpaid().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal havePay = order.getTotalAmount().subtract(order.getHadpaid());

            text = "Bạn đã đặt hàng thành công, mã số đơn hàng: " + order.getOrderID() + "\n" +
                    "Mã thanh toán: " + order.getVnp_TransactionNo() + "\n" +
                    "Số tiền đã thanh toán: " + order.getHadpaid() + "\n" +
                    "Giá trị đơn hàng: " + order.getTotalAmount() + "\n" +
                    "Số tiền còn lại cần thanh toán: " + havePay + "\n" +
                    "Hãy lưu giữ lại mã thanh toán và mã số đơn hàng nếu có khiếu nại hoặc yêu cầu hoàn trả tiền.";
        } else {
            text = "Bạn đã đặt hàng thành công, mã số đơn hàng: " + order.getOrderID() + "\n" +
                    "Giá trị đơn hàng: " + order.getTotalAmount();
        }
        emailService.sendSimpleMessage(email, subject, text);
    }

    public void PreorderSuccess(Preorder preorder,int idAccount) {
        Account account = accountService.getAccountById(idAccount);
        String email = account.getEmail();
        String subject = "Thông báo đặt trước thành công";

        BigDecimal hadPaid = BigDecimal.ZERO;

        List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(preorder);
        for (Preorderdetail detail : preorderdetails) {
            hadPaid = hadPaid.add(detail.getPaid());
        }

        BigDecimal remainingAmount = preorder.getTotalAmount().subtract(hadPaid);
        String text = "Bạn đã đặt trước thành công, mã số đơn đặt trước: " + preorder.getId() + "\n" +
                "Mã thanh toán: " + preorder.getVnp_TransactionNo() + "\n" +
                "Số tiền đã thanh toán: " + hadPaid + "\n" +
                "Giá trị đơn đặt trước: " + preorder.getTotalAmount() + "\n" +
                "Số tiền còn lại cần thanh toán: " + remainingAmount + "\n" +
                "Hãy lưu giữ lại mã thanh toán và mã số đơn đặt trước nếu có khiếu nại hoặc yêu cầu hoàn trả tiền.";

        emailService.sendSimpleMessage(email, subject, text);
    }

    public void OrderCondition(Order order, String text) {
        Account account = order.getAccountID();
        String email = account.getEmail();
        String subject = "Thông báo trạng thái đơn hàng";
        emailService.sendSimpleMessage(email, subject, text);
    }

    public void PreOrderCondition(Preorder preorder, String text) {
        Account account = preorder.getAccount();
        String email = account.getEmail();
        String subject = "Thông báo trạng thái đơn đặt trước";
        emailService.sendSimpleMessage(email, subject, text);
    }

    public void OrderCancelFail(Order order, String text) {
        Account account = order.getAccountID();
        String email = account.getEmail();
        String subject = "Thông báo yêu cầu hủy thất bại";
        emailService.sendSimpleMessage(email, subject, text);
    }

    public void PreorderToOrder(Preorder preorder, Order order) {
        Account account = preorder.getAccount();
        String email = account.getEmail();
        String subject = "Thông báo đơn đặt trước đã sẵn sàng";
        String text = "Đơn đặt trước "+preorder.getId()+" của bạn đã sẵn sàng"+ "\n" +
                "Mã thanh toán: "+preorder.getVnp_TransactionNo() +"\n"+
                "Mã đơn hàng: " + order.getOrderID() +"\n"+
                "Hãy kiểm tra lại thông tin, nếu có sai sót hãy liên hệ vơới chúng tôi sớm nhất: http://localhost:8000/account/history/" + order.getOrderID();
        emailService.sendSimpleMessage(email, subject, text);
    }

    public void OrDeSuccess(OrderDelivery orderDelivery, int idAccount) {
        Account account = accountService.getAccountById(idAccount);
        String email = account.getEmail();
        String subject = "Thông báo đặt hẹn giao hàng thành công";
        String text;
        text = "Bạn đã thanh toán thành công, mã số đơn hẹn giao hàng: " + orderDelivery.getId() + "\n" +
                    "Mã thanh toán: " + orderDelivery.getVnp_TransactionNo() + "\n" +
                    "Số tiền đã thanh toán: " + orderDelivery.getTotal() + "\n" +
                    "Hãy lưu giữ lại mã thanh toán và mã số đơn hẹn nếu có khiếu nại hoặc hủy đơn.";

        emailService.sendSimpleMessage(email, subject, text);
    }
}

