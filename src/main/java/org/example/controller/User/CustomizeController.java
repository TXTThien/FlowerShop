package org.example.controller.User;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.example.controller.EmailController;
import org.example.controller.NotificationController;
import org.example.dto.FlowerChoose;
import org.example.dto.OrderDeliveryDTO;
import org.example.dto.PostCustom;
import org.example.entity.*;
import org.example.entity.enums.CustomCondition;
import org.example.entity.enums.Deliverper;
import org.example.entity.enums.Status;
import org.example.repository.CustomizeRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customize")
@RequiredArgsConstructor
public class CustomizeController {
    private final ICustomizeService customizeService;
    private final IFlowerCustomService flowerCustomService;
    private final IOtherCustomService otherCustomService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final CustomizeRepository customizeRepository;
    private final ITypeService typeService;
    private final NotificationController notificationController;
    private final EmailController emailController;
    @PostMapping("/customize")
    public ResponseEntity<?> postCustomize(@RequestBody PostCustom postCustom) {
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);

        Customize customize = new Customize();
        customize.setName(postCustom.getName());
        customize.setPhoneNumber(postCustom.getPhone());
        customize.setDeliveryAddress(postCustom.getAddress());
        customize.setNote(postCustom.getNote());
        customize.setDate(LocalDateTime.now());
        customize.setStatus(Status.ENABLE);
        customize.setDescription(postCustom.getDescription());
        customize.setPurpose(postCustom.getPurpose());
        customize.setSentence(postCustom.getSentence());
        customize.setCondition(CustomCondition.PROCESSING);
        customize.setAccountID(account);
        customize.setNumber(postCustom.getNumber());

        customizeRepository.save(customize);

        return ResponseEntity.ok("Customize request submitted successfully.");
    }


    public void updateCustomize(BigDecimal total, int customizeid, int accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            Customize customize = customizeService.findCustomizeByID(customizeid);
            customize.setCondition(CustomCondition.PAID);
            customizeRepository.save(customize);

            BigDecimal consume = account.getConsume().add(total);
            account.setConsume(consume);
            List<Type> types = typeService.findAllOrderByMinConsumeAsc();

            Type appropriateType = null;
            for (Type type : types) {
                if (consume.compareTo(type.getMinConsume()) >= 0) {
                    appropriateType = type;
                } else {
                    break;
                }
            }
            if (appropriateType != null) {
                account.setType(appropriateType);
            }

            accountService.save(account);

            emailController.CustomizeSuccess(customize, accountId);

            ResponseEntity.status(HttpStatus.CREATED)
                    .body("Order Delivery purchased successfully.");
        } catch (
                Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating Order Delivery: " + e.getMessage());
        }
    }
}
