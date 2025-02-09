package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Precondition;
import org.example.entity.enums.Status;
import org.example.repository.OrderRepository;
import org.example.repository.PreOrderRepository;
import org.example.repository.PreorderdetailRepository;
import org.example.repository.RefundResponsitory;
import org.example.service.IAccountService;
import org.example.service.IPreorderdetailService;
import org.example.service.IRefundService;
import org.example.service.ITypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/staff/refund")
@RequiredArgsConstructor
public class StaffRefundRequestController {
    private final RefundResponsitory refundResponsitory;
    private final IPreorderdetailService preorderdetailService;
    private final PreOrderRepository preOrderRepository;
    private final OrderRepository orderRepository;
    private final ITypeService typeService;
    private final IAccountService accountService;
    private final IRefundService refundService;
    @GetMapping("")
    public ResponseEntity<?> getRefund(){
        List<Refund> refunds = refundResponsitory.findAll();

        Collections.reverse(refunds);
        Map<String, Object> response = new HashMap<>();
        response.put("refunds", refunds);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public void editRefund(@PathVariable int id, @RequestBody Refund newrefund){
        Refund refund = refundResponsitory.findRefundById(id);
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

        if (refund.getPreorderID() == null) {
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
        } else {
            Preorder preorder = refund.getPreorderID();


            preorder.setPrecondition(Precondition.Cancel);
            Account account = preorder.getAccount();


            BigDecimal hadPaid = BigDecimal.ZERO;
            List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(preorder);
            for (Preorderdetail detail : preorderdetails) {
                hadPaid = hadPaid.add(detail.getPaid().multiply(BigDecimal.valueOf(detail.getQuantity())));
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
        }
    }

}
