package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.FlowerSize;
import org.example.entity.Type;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Role;
import org.example.repository.AccountRepository;
import org.example.repository.FlowerSizeRepository;
import org.example.repository.OrderRepository;
import org.example.repository.TypeRepository;
import org.example.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboard {
    private  final IAccountService accountService;
    private final TypeRepository typeRepository;
    private  final AccountRepository accountRepository;
    private final FlowerSizeRepository flowerSizeRepository;
    private final OrderRepository orderRepository;

    @GetMapping("")
    public ResponseEntity<?> getDashBoard(){
        List<Account> accountUser = accountService.getAccountByRole(Role.user);
        List<Account> accountAdmin = accountService.getAccountByRole(Role.admin);
        List<Account> accountStaff = accountService.getAccountByRole(Role.staff);
        List<Account> accountShipper = accountService.getAccountByRole(Role.shipper);
        int numberUser = accountUser.size();
        int numberAdmin = accountAdmin.size();
        int numberStaff = accountStaff.size();
        int numberShipper = accountShipper.size();

        List<Map<String, Object>> typeDetails = new ArrayList<>();
        List<Integer> accountCounts = new ArrayList<>();

        // Xử lý dữ liệu từ kết quả truy vấn
        for (Object[] result : accountRepository.countAccountsByType()) {
            Integer typeID = (Integer) result[0]; // Lấy typeID
            Integer count = ((Number) result[1]).intValue(); // Lấy số lượng tài khoản

            if (typeID != null) {
                typeRepository.findById(typeID).ifPresent(type -> {
                    // Thêm thông tin typeID và typeName vào danh sách
                    Map<String, Object> typeDetail = new HashMap<>();
                    typeDetail.put("typeID", type.getTypeID());
                    typeDetail.put("typeName", type.getTypeName());
                    typeDetails.add(typeDetail);

                    // Thêm số lượng tài khoản vào danh sách
                    accountCounts.add(count);
                });
            }
        }


        BigDecimal totalCost = flowerSizeRepository.calculateCost();
        BigDecimal totalGetYes = orderRepository.calculateGet(IsPaid.Yes);
        BigDecimal totalGetNo = orderRepository.calculateGet(IsPaid.No);

        Map<String, Object> response = new HashMap<>();
        response.put("numberUser", numberUser);
        response.put("numberAdmin", numberAdmin);
        response.put("numberStaff", numberStaff);
        response.put("numberShipper", numberShipper);
        response.put("typeDetails", typeDetails); // Danh sách thông tin Type
        response.put("accountCounts", accountCounts);
        response.put("totalCost", totalCost);
        response.put("totalGetYes", totalGetYes);
        response.put("totalPaidNo", totalGetNo);



        return ResponseEntity.ok(response);
    }
}
