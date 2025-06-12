package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostCustom;
import org.example.entity.Account;
import org.example.entity.CustomDetail;
import org.example.entity.Customize;
import org.example.entity.enums.CustomCondition;
import org.example.entity.enums.Status;
import org.example.repository.CustomizeRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acccus")
@RequiredArgsConstructor
public class UserCustomizeController {
    private final ICustomizeService customizeService;
    private final IFlowerCustomService flowerCustomService;
    private final IOtherCustomService otherCustomService;
    private final ICustomDetailService customDetailService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final CustomizeRepository customizeRepository;
    @GetMapping("")
    public ResponseEntity<?> getCustomize() {
        int accountid = getIDAccountFromAuthService.common();
        List<Customize> customizes = customizeService.findMyCustomize(accountid);
        Map<String, Object> response = new HashMap<>();
        response.put("customize", customizes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailCustomize(@PathVariable int id) {

        Customize customize = customizeService.findCustomizeByID(id);
        List<CustomDetail> customDetails = customDetailService.findByCustomID(id);
        Map<String, Object> response = new HashMap<>();
        response.put("customize", customize);
        response.put("customDetails", customDetails);

        return ResponseEntity.ok(response);
    }
}
