package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.auth.ChangePassword;
import org.example.dto.BuyHistoryDTO;
import org.example.dto.OrderHistory;
import org.example.entity.Account;
import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.Review;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.service.IAccountService;
import org.example.service.IOrderDetailService;
import org.example.service.IOrderService;
import org.example.service.IReviewService;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.securityService.AuthService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            buyHistoryDTO.setProductTitle(billInfo.getFlowerSize().getFlower().getName()+ " " + billInfo.getFlowerSize().getSizeName());
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
            orderHistory.setCondition(order.getCondition());
            orderHistory.setNote(order.getNote());
            orderHistory.setShipStart(order.getShipping().getStartDate());
            orderHistory.setShipEnd(order.getShipping().getCompleteDate());
            orderHistory.setShipperName(order.getShipping().getAccountID().getName());
            orderHistory.setShipperPhone(order.getShipping().getAccountID().getPhoneNumber());
            orderHistory.setShipperEmail(order.getShipping().getAccountID().getEmail());

            orderHistories.add(orderHistory);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orderHistory", orderHistories);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateinfo")
    public ResponseEntity<String> updateAccountByAccountID(
            @RequestBody Account updateAccountRequest) {
        int idAccount = getIDAccountService.common();


        Account currentAccount = accountRepository.findAccountByAccountID(idAccount);

        if (currentAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại.");
        }

        if (updateAccountRequest.getName() != null) {
            currentAccount.setName(updateAccountRequest.getName());
        }
        if (updateAccountRequest.getPhoneNumber() != null) {
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
    public ResponseEntity<Void> cancelOrder(@RequestParam Integer OrderID){
        Order order = orderService.findOrderByOrderID(OrderID);
        if (order == null)
            return ResponseEntity.notFound().build();
        if (order.getCondition() == Condition.Prepare || order.getCondition() == Condition.Pending || order.getCondition() == Condition.Processing)
        {
            order.setCondition(Condition.Cancelled);
            orderService.update(order);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();

    }

}