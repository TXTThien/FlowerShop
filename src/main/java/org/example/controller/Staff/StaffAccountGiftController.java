package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.*;
import org.example.service.IAccountGiftService;
import org.example.service.IAccountService;
import org.example.service.IDiscountService;
import org.example.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staffaccountgift")
@RequiredArgsConstructor
public class StaffAccountGiftController {
    private final IAccountGiftService accountGiftService;
    private final AccountRepository accountRepository;
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;
    private final AccountGiftRepository accountGiftRepository;
    private final GiftRepository giftRepository;
    @GetMapping("")
    public ResponseEntity<?> getAccountGiftInfo()
    {
        List<AccountGift> accountGifts = accountGiftService.findAllByAdmin();
        List<Account> accounts = accountRepository.findAll();
        List<Discount> discounts = discountRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<Gift> gifts = giftRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("gifts", gifts);
        response.put("accountGifts", accountGifts);
        response.put("accounts", accounts);
        response.put("discounts", discounts);
        response.put("orders", orders);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailAccountGiftInfo(@PathVariable int id)
    {
        AccountGift accountGifts = accountGiftService.findByIDByAdmin(id);
        List<Account> accounts = accountRepository.findAll();
        List<Discount> discounts = discountRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<Gift> gifts = giftRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("gifts",gifts);
        response.put("accountGift", accountGifts);
        response.put("accounts", accounts);
        response.put("discounts", discounts);
        response.put("orders", orders);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putDetailAccountGiftInfo(@PathVariable int id, @RequestBody AccountGift accountGift)
    {
        AccountGift accountGifts = accountGiftService.findByIDByAdmin(id);
        accountGifts.setAccount(accountGift.getAccount());
        accountGifts.setGift(accountGift.getGift());
        accountGifts.setOrder(accountGift.getOrder());
        accountGifts.setStatus(accountGift.getStatus());
        accountGifts.setDiscount(accountGift.getDiscount());
        accountGiftRepository.save(accountGifts);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("")
    public ResponseEntity<?> postDetailAccountGiftInfo(@RequestBody AccountGift accountGift)
    {
        AccountGift accountGifts = new AccountGift();
        accountGifts.setAccount(accountGift.getAccount());
        accountGifts.setGift(accountGift.getGift());
        accountGifts.setOrder(accountGift.getOrder());
        accountGifts.setDate(LocalDate.now());
        accountGifts.setStatus(accountGift.getStatus());
        accountGifts.setDiscount(accountGift.getDiscount());
        accountGiftRepository.save(accountGifts);

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountGift(@PathVariable int id)
    {
        AccountGift accountGifts = accountGiftService.findByIDByAdmin(id);
        if (accountGifts.getStatus() == Status.ENABLE)
            accountGifts.setStatus(Status.DISABLE);
        else
            accountGifts.setStatus(Status.ENABLE);
        accountGiftRepository.save(accountGifts);
        return ResponseEntity.ok("Success");

    }

}
