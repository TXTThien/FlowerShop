package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.controller.NotificationController;
import org.example.dto.AdminStaffRefund;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.OrDeCondition;
import org.example.entity.enums.Precondition;
import org.example.entity.enums.Status;
import org.example.repository.OrderDeliveryRepository;
import org.example.repository.OrderRepository;
import org.example.repository.PreOrderRepository;
import org.example.repository.RefundResponsitory;
import org.example.service.IAccountService;
import org.example.service.IPreorderdetailService;
import org.example.service.IRefundService;
import org.example.service.ITypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/refund")
@RequiredArgsConstructor
public class AdminRefundRequestController {
    private final RefundResponsitory refundResponsitory;
    private final IPreorderdetailService preorderdetailService;
    private final PreOrderRepository preOrderRepository;
    private final OrderRepository orderRepository;
    private final ITypeService typeService;
    private final IAccountService accountService;
    private final IRefundService refundService;
    private final NotificationController notificationController;
    private final OrderDeliveryRepository orderDeliveryRepository;
    @GetMapping("")
    public ResponseEntity<?> getRefund() {
        List<Refund> refunds = refundResponsitory.findAll();
        List<AdminStaffRefund> adminStaffRefund = new ArrayList<>();

        for (int i = 0; i < refunds.size(); i++) {
            AdminStaffRefund staffRefund = new AdminStaffRefund(); // Tạo đối tượng mới
            staffRefund.setRefund(refunds.get(i));

            if (refunds.get(i).getOrderID() != null) {
                staffRefund.setRefundMoney(refunds.get(i).getOrderID().getHadpaid());
            } else if (refunds.get(i).getPreorderID() != null) {
                BigDecimal total = BigDecimal.ZERO;
                List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(refunds.get(i).getPreorderID());
                for (Preorderdetail detail : preorderdetails) {
                    total = total.add(detail.getPaid());
                }
                staffRefund.setRefundMoney(total);
            } else if (refunds.get(i).getOrderdeliveryid() != null){
                System.out.println("Refundmoney"+refunds.get(i).getOrderdeliveryid().getRefund());
                staffRefund.setRefundMoney(refunds.get(i).getOrderdeliveryid().getRefund());
            }
            else
                staffRefund.setRefundMoney(BigDecimal.ZERO);


            adminStaffRefund.add(staffRefund); // Thêm vào danh sách
        }

        Map<String, Object> response = new HashMap<>();
        response.put("refunds", adminStaffRefund);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public void editRefund(@PathVariable int id, @RequestBody Refund newrefund){
        Refund refund = refundResponsitory.findRefundById(id);
        refund.setOrderID(newrefund.getOrderID());
        refund.setPreorderID(newrefund.getPreorderID());
        refund.setDate(newrefund.getDate());
        refund.setNumber(newrefund.getNumber());
        refund.setBank(newrefund.getBank());
        refund.setStatus(newrefund.getStatus());
        refundResponsitory.save(refund);
    }

    @RequestMapping("/{id}/complete")
    public void completeRefund(@PathVariable int id) {
        Refund refund = refundResponsitory.findRefundById(id);
        refund.setStatus(Status.DISABLE);
        refundResponsitory.save(refund);

        if (refund.getOrderID() != null) {
            Order order = refund.getOrderID();

            order.setCondition(Condition.Cancelled);
            Account account = order.getAccountID();

            // Trừ tiền đã trả từ tài khoản
            account.setConsume(account.getConsume().subtract(order.getHadpaid()));
            // Xác định cấp độ mới của tài khoản
            List<Type> types = typeService.findAllOrderByMinConsumeAsc();
            Type appropriateType = null;
            for (Type type : types) {
                if (account.getConsume().compareTo(type.getMinConsume()) >= 0) {
                    appropriateType = type;
                } else {
                    break;
                }
            }
            if (appropriateType != null) {
                account.setType(appropriateType);
            }

            orderRepository.save(order);
            accountService.save(account);
            notificationController.orderConditionNotification(order.getOrderID());
        } else  if (refund.getPreorderID() != null){
            Preorder preorder = refund.getPreorderID();


            preorder.setPrecondition(Precondition.Cancel);
            Account account = preorder.getAccount();


            BigDecimal hadPaid = BigDecimal.ZERO;
            List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(preorder);
            for (Preorderdetail detail : preorderdetails) {
                hadPaid = hadPaid.add(detail.getPaid());
            }

            account.setConsume(account.getConsume().subtract(hadPaid));

            List<Type> types = typeService.findAllOrderByMinConsumeAsc();
            Type appropriateType = null;
            for (Type type : types) {
                if (account.getConsume().compareTo(type.getMinConsume()) >= 0) {
                    appropriateType = type;
                } else {
                    break;
                }
            }
            if (appropriateType != null) {
                account.setType(appropriateType);
            }

            preOrderRepository.save(preorder);
            accountService.save(account);
            notificationController.refundPreorder(preorder.getId());
        }
        else  {
            OrderDelivery orderDelivery1 = refund.getOrderdeliveryid();


            orderDelivery1.setCondition(OrDeCondition.CANCEL);
            Account account = orderDelivery1.getAccountID();

            account.setConsume(account.getConsume().subtract(orderDelivery1.getRefund()));
            List<Type> types = typeService.findAllOrderByMinConsumeAsc();
            Type appropriateType = null;
            for (Type type : types) {
                if (account.getConsume().compareTo(type.getMinConsume()) >= 0) {
                    appropriateType = type;
                } else {
                    break;
                }
            }
            if (appropriateType != null) {
                account.setType(appropriateType);
            }

            orderDeliveryRepository.save(orderDelivery1);
            accountService.save(account);
            notificationController.refundOrDe(orderDelivery1.getId());
        }
    }
}
