package org.example.controller.User;

import com.google.type.DateTime;
import lombok.RequiredArgsConstructor;
import org.example.dto.GiftInfoDTO;
import org.example.dto.RollDTO;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Status;
import org.example.entity.enums.TypeGift;
import org.example.repository.*;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final IAttendanceService attendanceService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final AttendanceRepository attendanceRepository;
    private final IAccountService accountService;
    private final IRollBarService rollBarService;
    private final IAccountGiftService accountGiftService;
    private final IGiftService giftService;
    private final AccountGiftRepository accountGiftRepository;
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final IDiscountService discountService;
    @GetMapping("")
    private ResponseEntity<?> getAttendance() {
        int account = getIDAccountFromAuthService.common();
        int month = LocalDateTime.now().getMonthValue();
        List<Attendance> attendanceList = attendanceService.findAttendanceByAccountAndMonth(account, month);
        Map<String, Object> response = new HashMap<>();
        response.put("attendanceList", attendanceList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gift")
    private ResponseEntity<?> getGift() {
        int account = getIDAccountFromAuthService.common();
        List<AccountGift> accountGifts = accountGiftService.findAccountGiftByAccountID(account);
        Map<String, Object> response = new HashMap<>();
        response.put("accountGifts", accountGifts);
        return ResponseEntity.ok(response);
    }

    @RequestMapping("/check")
    private ResponseEntity<?> checkToday() {
        int account = getIDAccountFromAuthService.common();
        LocalDateTime localDateTime = LocalDateTime.now();
        Attendance nowAttendance = attendanceService.findAttendanceByAccountAndDate(account, localDateTime);
        if (nowAttendance == null) {
            Attendance checkNow = new Attendance();
            checkNow.setAccount(accountService.getAccountById(account));
            checkNow.setDate(LocalDateTime.now());
            checkNow.setStatus(Status.ENABLE);
            attendanceRepository.save(checkNow);
            return ResponseEntity.ok("Attendance Success");
        } else return ResponseEntity.badRequest().body("You had attendanced before");
    }

    @RequestMapping("/roll/{id}")
    private ResponseEntity<?> rollGift(@RequestBody Integer giftid, @PathVariable int id) {
        int accountid = getIDAccountFromAuthService.common();
        int month = LocalDateTime.now().getMonthValue();
        List<Attendance> attendanceList = attendanceService.findAttendanceByAccountAndMonth(accountid, month);
        RollBar rollBar = rollBarService.findRollBarById(id);

        if (attendanceList.size() < rollBar.getDays()) {
            return ResponseEntity.badRequest().body("Bạn không đủ số lượt điểm danh, hãy điểm danh thêm hoặc chọn vòng quay khác.");
        }

        List<AccountGift> accountGifts = accountGiftService.findAccountGiftByAccountIDAndMonth(accountid, month);
        if (accountGifts != null && !accountGifts.isEmpty()) {
            return ResponseEntity.badRequest().body("Tháng này bạn đã dùng vòng quay rồi, hãy chờ tháng sau nhé!");
        }

        AccountGift accountGift = new AccountGift();
        accountGift.setAccount(accountService.getAccountById(accountid));
        accountGift.setDate(LocalDate.now());
        accountGift.setStatus(Status.ENABLE);
        accountGift.setGift(giftService.findGiftByGiftID(giftid));
        accountGiftRepository.save(accountGift);

        Map<String, Object> response = new HashMap<>();

        if (accountGift.getGift().getTypeGift() == TypeGift.discount) {
            Gift gift = giftService.findGiftByGiftID(giftid); // dùng đúng giftid
            Discount discount = new Discount();
            discount.setStatus(Status.ENABLE);
            discount.setDiscountPercent(gift.getDiscountpercent());
            discount.setPurpose(gift.getPurpose());
            discount.setType(gift.getType());
            discount.setCategoryID(gift.getCategoryID());
            discount.setAccount(accountService.getAccountById(accountid));
            discount.setStartDate(LocalDateTime.now());
            discount.setEndDate(gift.getTimeEnd());
            discount.setDiscountcode(generateUniqueDiscountCode());
            discountRepository.save(discount);

            accountGift.setDiscount(discount);
            response.put("accountGift", accountGift);

            return ResponseEntity.ok(response);
        }

        response.put("accountGift", accountGift);
        return ResponseEntity.ok(response);
    }


    public String generateUniqueDiscountCode() {
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        // Sử dụng Set thay vì List để tăng tốc độ tìm kiếm
        Set<String> existingCodes = discountService.findAllCode()
                .stream()
                .map(Discount::getDiscountcode)
                .collect(Collectors.toSet());

        String name;
        do {
            StringBuilder builder = new StringBuilder("var_");
            for (int i = 0; i < 9; i++) {
                builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            }
            name = builder.toString();
        } while (existingCodes.contains(name));

        return name;
    }

    @RequestMapping("/sendInfo/{id}")
    private ResponseEntity<?> sendInfo(@RequestParam RollDTO rollDTO, @PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();
        AccountGift accountGift = accountGiftService.findById(id);
        Order order = new Order();
        order.setNote(rollDTO.getNote());
        order.setName(rollDTO.getName());
        order.setPhoneNumber(rollDTO.getPhone());
        order.setDeliveryAddress(rollDTO.getAddress());
        order.setAccountID(accountService.getAccountById(accountid));
        order.setDate(LocalDateTime.now());
        order.setStatus(Status.ENABLE);
        order.setPaid(IsPaid.Yes);
        order.setTotalAmount(accountGift.getGift().getFlowersizeid().getPrice());
        order.setHadpaid(accountGift.getGift().getFlowersizeid().getPrice());
        order.setCondition(Condition.In_Transit);

        orderRepository.save(order);

        OrderDetail orderDetail= new OrderDetail();
        orderDetail.setFlowerSize(accountGift.getGift().getFlowersizeid());
        orderDetail.setQuantity(1);
        orderDetail.setOrderID(order);
        orderDetail.setPrice(accountGift.getGift().getFlowersizeid().getPrice());
        orderDetail.setPaid(accountGift.getGift().getFlowersizeid().getPrice());
        orderDetail.setStatus(Status.ENABLE);

        orderDetailRepository.save(orderDetail);

        accountGift.setOrder(order);
        Map<String, Object> response = new HashMap<>();
        response.put("accountGift", accountGift);
        return ResponseEntity.ok(response);
    }

}
