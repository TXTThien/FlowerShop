package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.controller.NotificationController;
import org.example.dto.AcceptDTO;
import org.example.dto.CustomizeDetailDTO;
import org.example.dto.EditCustomize;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.CustomCondition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Status;
import org.example.repository.*;
import org.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/custom")
@RequiredArgsConstructor
public class AdminCustomizeController {
    private final ICustomizeService customizeService;
    private final IFlowerCustomService flowerCustomService;
    private final IOtherCustomService otherCustomService;
    private final CustomizeRepository customizeRepository;
    private final ICustomDetailService customDetailService;
    private final FlowerCustomRepository flowerCustomRepository;
    private final OtherCustomRepository otherCustomRepository;
    private final CustomDetailRepository customDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final IFlowerSizeService flowerSizeService;
    private final NotificationController notificationController;
    @GetMapping("")
    public ResponseEntity<?> getAllCustomize() {
        List<Customize> customizes = customizeRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("customize", customizes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailCustomize(@PathVariable int id) {

        Customize customize = customizeService.findCustomizeByID(id);
        List<FlowerCustom> flowerCustoms = flowerCustomRepository.findFlowerCustomsByStatus(Status.ENABLE);
        List<OtherCustom> otherCustoms = otherCustomRepository.findOtherCustomsByStatus(Status.ENABLE);
        List<CustomDetail> customDetails = customDetailService.findByCustomID(id);
        Map<String, Object> response = new HashMap<>();
        response.put("customize", customize);
        response.put("customDetails", customDetails);
        response.put("flowerCustoms", flowerCustoms);
        response.put("otherCustoms", otherCustoms);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptCustomize(@RequestBody AcceptDTO acceptDTO, @PathVariable int id) {
        Customize customize = customizeService.findCustomizeByID(id);
        if (customize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customize not found with ID: " + id);
        }

        customize.setImage(acceptDTO.getImageurl());
        customize.setTotalAmount(acceptDTO.getTotal());

        List<CustomizeDetailDTO> customizeDetailDTOList = acceptDTO.getCustomizeDetailDTOList();
        if (customizeDetailDTOList != null) {
            for (CustomizeDetailDTO dto : customizeDetailDTOList) {
                CustomDetail detail = new CustomDetail();
                if (dto.getFlowerid() != null) {
                    detail.setFlower(flowerCustomService.findFlowerByID(dto.getFlowerid()));
                } else if (dto.getOtherid() != null) {
                    detail.setOther(otherCustomService.findOtherByID(dto.getOtherid()));
                } else {
                    return ResponseEntity.badRequest().body("Either flowerid or otherid must be provided for each item.");
                }
                detail.setNumber(dto.getNumber());
                detail.setCustomize(customize);
                customDetailRepository.save(detail);
            }
        }

        customize.setCondition(CustomCondition.ACCEPT);
        customizeRepository.save(customize);

        return ResponseEntity.ok("Customize accepted successfully.");
    }


    @PutMapping("/{id}/decline")
    public ResponseEntity<?> declineCustomize(@PathVariable int id) {
        Customize customize = customizeService.findCustomizeByID(id);
        if (customize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customize request not found with ID: " + id);
        }

        customize.setCondition(CustomCondition.CANCEL);
        customizeRepository.save(customize);

        return ResponseEntity.ok("Customize request declined successfully.");
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelCustomize(@PathVariable int id) {
        Customize customize = customizeService.findCustomizeByID(id);
        if (customize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customize request not found with ID: " + id);
        }

        customize.setCondition(CustomCondition.CANCEL);

        customizeRepository.save(customize);

        return ResponseEntity.ok("Customize request declined successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> cancelCustomize(@PathVariable int id, @RequestBody EditCustomize editCustomize) {
        Customize customize = customizeService.findCustomizeByID(id);
        if (customize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customize request not found with ID: " + id);
        }

        customize.setCondition(CustomCondition.valueOf(editCustomize.getCondition().trim().toUpperCase()));
        customize.setName(editCustomize.getName());
        customize.setNote(editCustomize.getNote());
        customize.setPhoneNumber(editCustomize.getPhone());
        customize.setDeliveryAddress(editCustomize.getAddress());
        customizeRepository.save(customize);

        return ResponseEntity.ok("Customize request declined successfully.");
    }

    @PutMapping("/{id}/success")
    public ResponseEntity<?> successCustomize(@PathVariable int id) {
        Customize customize = customizeService.findCustomizeByID(id);
        if (customize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customize request not found with ID: " + id);
        }

        customize.setCondition(CustomCondition.SUCCESS);
        customizeRepository.save(customize);

        // Create new Order
        Order order = new Order();
        order.setAccountID(customize.getAccountID());
        order.setDate(customize.getDate());
        order.setPaid(IsPaid.Yes);
        order.setTotalAmount(customize.getTotalAmount());
        order.setDeliveryAddress(customize.getDeliveryAddress());
        order.setPhoneNumber(customize.getPhoneNumber());
        order.setName(customize.getName());
        order.setNote(customize.getNote());
        order.setStatus(Status.ENABLE);
        order.setCondition(Condition.In_Transit);
        order.setHadpaid(customize.getTotalAmount());

        orderRepository.save(order);

        // Create order detail
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderID(order);
        orderDetail.setFlowerSize(flowerSizeService.findFlowerSizeByID(135)); // Consider making this dynamic
        orderDetail.setQuantity(customize.getNumber() != null ? customize.getNumber() : 1);
        orderDetail.setPrice(BigDecimal.ZERO);
        orderDetail.setPaid(BigDecimal.ZERO);
        orderDetail.setStatus(Status.ENABLE);

        orderDetailRepository.save(orderDetail);

        // Send notification
        notificationController.preOrderSuccessNotification(customize.getCustomID(), order.getOrderID());

        return ResponseEntity.ok("Customize marked as success and order created.");
    }

}
