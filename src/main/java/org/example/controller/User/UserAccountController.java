package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.auth.ChangePassword;
import org.example.dto.BuyHistoryDTO;
import org.example.dto.OrderHistory;
import org.example.dto.RefundRequest;
import org.example.entity.*;
import org.example.entity.enums.*;
import org.example.repository.*;
import org.example.service.*;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.securityService.AuthService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final AccountRepository accountRepository;
    private final IAccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final IOrderDetailService orderDetailService;
    private final IReviewService reviewService;
    private final IOrderService orderService;
    private final PreOrderRepository preOrderRepository;
    private final IPreOrderService preOrderService;
    private final IPreorderdetailService preorderdetailService;
    private final RefundResponsitory refundResponsitory;
    private final IFlowerSizeService flowerSizeService;
    private final FlowerSizeRepository flowerSizeRepository;
    private final OrderRepository orderRepository;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    @GetMapping("")
    public ResponseEntity<Account> getAccountInfo() {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/bought")
    public ResponseEntity<Map<String, Object>> getHistoryBought() {
        int idAccount = getIDAccountService.common();
        List<OrderDetail> billInfos = orderDetailService.findBillInfoByAccountID(idAccount, Status.ENABLE);
        List<BuyHistoryDTO> buyHistoryDTOS = new ArrayList<>();
        for (OrderDetail billInfo : billInfos) {
            BuyHistoryDTO buyHistoryDTO = new BuyHistoryDTO();
            buyHistoryDTO.setDate(billInfo.getOrderID().getDate());
            buyHistoryDTO.setNumber(billInfo.getQuantity());
            buyHistoryDTO.setProductID(billInfo.getFlowerSize().getFlower().getFlowerID());
            buyHistoryDTO.setProductTitle(billInfo.getFlowerSize().getFlower().getName() + " " + billInfo.getFlowerSize().getSizeName());
            buyHistoryDTO.setCost(billInfo.getFlowerSize().getPrice());
            buyHistoryDTOS.add(buyHistoryDTO);
        }

        List<Review> review = reviewService.findReviewByAccountID(idAccount, Status.ENABLE);
        Map<String, Object> response = new HashMap<>();
        response.put("billInfo", buyHistoryDTOS);
        response.put("review", review);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orderHistory")
    public ResponseEntity<Map<String, Object>> getOrderBought() {
        int idAccount = getIDAccountService.common();
        List<Order> orders = orderService.findOrderByAccountID(idAccount, Status.ENABLE);
        List<OrderHistory> orderHistories = new ArrayList<>();
        for (Order order : orders) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrderID(order.getOrderID());
            orderHistory.setDate(order.getDate());
            orderHistory.setIsPaid(order.getPaid());
            orderHistory.setTotal(order.getTotalAmount());
            orderHistory.setAddress(order.getDeliveryAddress());
            orderHistory.setPhone(order.getPhoneNumber());
            orderHistory.setName(order.getName());
            orderHistory.setPaid(order.getHadpaid());
            orderHistory.setCondition(order.getCondition());
            if (order.getNote() != null) {
                orderHistory.setNote(order.getNote());
            }
            if (order.getShipping() != null) {
                if (order.getShipping().getStartDate() != null) {
                    orderHistory.setShipStart(order.getShipping().getStartDate());
                }
                if (order.getShipping().getCompleteDate() != null) {
                    orderHistory.setShipEnd(order.getShipping().getCompleteDate());
                }
                if (order.getShipping().getNote() != null) {
                    orderHistory.setShipperNote(order.getShipping().getNote());
                }
                orderHistory.setShipperName(order.getShipping().getAccountID().getName());
                orderHistory.setShipperPhone(order.getShipping().getAccountID().getPhoneNumber());
                orderHistory.setShipperEmail(order.getShipping().getAccountID().getEmail());

            }
            orderHistories.add(orderHistory);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orderHistory", orderHistories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orderHistory/{id}")
    public ResponseEntity<Map<String, Object>> getDetailOrderBought(@PathVariable int id) {
        int idAccount = getIDAccountService.common();
        Order order = orderService.findOrderByOrderID(id);
        List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByOrderID(id);
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrderID(order.getOrderID());
        orderHistory.setDate(order.getDate());
        orderHistory.setIsPaid(order.getPaid());
        orderHistory.setTotal(order.getTotalAmount());
        orderHistory.setAddress(order.getDeliveryAddress());
        orderHistory.setPhone(order.getPhoneNumber());
        orderHistory.setName(order.getName());
        orderHistory.setPaid(order.getTotalAmount().subtract(order.getHadpaid()));
        orderHistory.setCondition(order.getCondition());
        if (order.getNote() != null) {
            orderHistory.setNote(order.getNote());
        }
        if (order.getShipping() != null) {
            if (order.getShipping().getStartDate() != null) {
                orderHistory.setShipStart(order.getShipping().getStartDate());
            }
            if (order.getShipping().getCompleteDate() != null) {
                orderHistory.setShipEnd(order.getShipping().getCompleteDate());
            }
            if (order.getShipping().getNote() != null) {
                orderHistory.setShipperNote(order.getShipping().getNote());
            }
            orderHistory.setShipperName(order.getShipping().getAccountID().getName());
            orderHistory.setShipperPhone(order.getShipping().getAccountID().getPhoneNumber());
            orderHistory.setShipperEmail(order.getShipping().getAccountID().getEmail());
        }


        Map<String, Object> response = new HashMap<>();
        response.put("orderHistory", orderHistory);
        response.put("orderDetail", orderDetails);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateinfo")
    public ResponseEntity<String> updateAccountByAccountID(
            @RequestBody Account updateAccountRequest) {
        int idAccount = getIDAccountService.common();
        System.out.println("Số điện thoại: " + updateAccountRequest.getPhoneNumber());
        Account currentAccount = accountRepository.findAccountByAccountID(idAccount);

        if (currentAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại.");
        }
        System.out.println("Vo2");

        if (updateAccountRequest.getName() != null) {
            System.out.println("Name: " + updateAccountRequest.getName());
            currentAccount.setName(updateAccountRequest.getName());
        }
        if (updateAccountRequest.getPhoneNumber() != null) {
            System.out.println("Số điện thoại: " + updateAccountRequest.getPhoneNumber());
            currentAccount.setPhoneNumber(updateAccountRequest.getPhoneNumber());
        }
        if (updateAccountRequest.getAddress() != null) {
            currentAccount.setAddress(updateAccountRequest.getAddress());
        }
        if (updateAccountRequest.getEmail() != null) {
            currentAccount.setEmail(updateAccountRequest.getEmail());
        }
        if (updateAccountRequest.getAvatar() != null) {
            currentAccount.setAvatar(updateAccountRequest.getAvatar());
        }
        accountService.updateAccountInfo(currentAccount);

        return ResponseEntity.ok("Cập nhật thông tin tài khoản thành công.");
    }

    @PutMapping("/changepassword")
    public ResponseEntity<String> updateChangePassword(
            @RequestBody ChangePassword changePassword) {

        int idAccount = getIDAccountService.common();
        String newpass = changePassword.getNewpass();
        String curpass = changePassword.getCurpass();

        Account account = accountRepository.findById(idAccount)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(curpass, account.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu hiện tại không đúng.");
        }

        String updatepass = passwordEncoder.encode(newpass);
        accountRepository.updatePassword(updatepass, idAccount);

        return ResponseEntity.ok("Đổi mật khẩu thành công.");
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(@RequestParam Integer orderID) {
        Order order = orderService.findOrderByOrderID(orderID);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        if (order.getCondition() == Condition.Prepare || order.getCondition() == Condition.Processing) {
            order.setCondition(Condition.Cancel_is_Processing);
            orderService.update(order);
            return ResponseEntity.noContent().build();
        } else if (order.getCondition() == Condition.Pending) {
            List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByOrderID(orderID);

            if (order.getHadpaid().equals(BigDecimal.ZERO)) {
                order.setCondition(Condition.Cancelled);
            } else {
                order.setCondition(Condition.Refund);
            }

            for (OrderDetail orderDetail : orderDetails) {
                FlowerSize flowerSize = flowerSizeService.findFlowerSizeByID(orderDetail.getFlowerSize().getFlowerSizeID());
                if (flowerSize != null) {
                    flowerSize.setStock(flowerSize.getStock() + orderDetail.getQuantity());
                    flowerSizeRepository.save(flowerSize);
                }
            }

            orderService.update(order);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/preorder")
    public ResponseEntity<Map<String, Object>> getPreOrder() {
        int idAccount = getIDAccountService.common();
        List<Preorder> preorders = preOrderService.findPreorderByAccount(idAccount);
        Map<String, Object> response = new HashMap<>();
        response.put("preorders", preorders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/preorder/{id}")
    public ResponseEntity<Map<String, Object>> getPreOrderDetail(@PathVariable int id) {
        Preorder preorder = preOrderService.findPreorderByPreorderID(id);
        List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(preorder);
        Map<String, Object> response = new HashMap<>();
        response.put("preorders", preorder);
        response.put("preorderdetails", preorderdetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/preorder/cancel")
    public ResponseEntity<Void> cancelPreorder(@RequestParam Integer id) {
        Preorder preorder = preOrderService.findPreorderByPreorderID(id);
        if (preorder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (preorder.getPrecondition() == Precondition.Waiting) {
            preorder.setPrecondition(Precondition.Refund);
            preOrderRepository.save(preorder);
            return ResponseEntity.noContent().build(); // Trả về 204
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RequestMapping("/order/{id}/refund")
    public ResponseEntity<String> makeRefundRequestorder(@PathVariable int id, @RequestBody RefundRequest refundRequest) {
        Order order = orderService.findOrderByOrderID(id);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đơn hàng không tồn tại.");
        }

        // Kiểm tra xem có bản ghi Refund nào đã tồn tại với orderID không
        boolean refundExists = refundResponsitory.existsByOrderID(order);
        if (refundExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đơn hàng này đã hoàn tiền.");
        }

        if (order.getAccountID().getAccountID() != getIDAccountService.common())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bạn không thể tương tác trên đơn hàng này.");
        }

        if (order.getCondition() != Condition.Refund)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tiến hành hoàn tiền đơn hàng này.");
        }

        if (Objects.equals(refundRequest.getVnp_TransactionNo(), order.getVnp_TransactionNo())) {
            Refund refund = new Refund();
            refund.setOrderID(order);
            refund.setBank(refundRequest.getBank());
            refund.setNumber(refundRequest.getNumber());
            refund.setStatus(Status.ENABLE);
            refund.setDate(LocalDateTime.now());
            refundResponsitory.save(refund);

            order.setCondition(Condition.Refund_is_Processing);
            orderRepository.save(order);

            return ResponseEntity.ok("Gửi yêu cầu hoàn tiền thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giao dịch không khớp.");
        }
    }

    @RequestMapping("/preorder/{id}/refund")
    public ResponseEntity<String> makeRefundRequestpreorder(@PathVariable int id, @RequestBody RefundRequest refundRequest) {
        Preorder preorder = preOrderService.findPreorderByPreorderID(id);

        if (preorder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đơn hàng không tồn tại.");
        }
        boolean refundExists = refundResponsitory.existsByPreorderID(preorder);
        if (refundExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đơn hàng này đã hoàn tiền.");
        }

        if (preorder.getAccount().getAccountID() != getIDAccountService.common())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bạn không thể tương tác trên đơn hàng này.");
        }

        if (preorder.getPrecondition() != Precondition.Refund)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tiến hành hoàn tiền đơn hàng này.");
        }
        if (Objects.equals(refundRequest.getVnp_TransactionNo(), preorder.getVnp_TransactionNo())) {
            Refund refund = new Refund();
            refund.setPreorderID(preorder);
            refund.setBank(refundRequest.getBank());
            refund.setNumber(refundRequest.getNumber());
            refund.setStatus(Status.ENABLE);
            refund.setDate(LocalDateTime.now());

            refundResponsitory.save(refund);

            preorder.setPrecondition(Precondition.Refunding);
            preOrderRepository.save(preorder);

            return ResponseEntity.ok("Gửi yêu cầu hoàn tiền thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giao dịch không khớp.");
        }
    }

}