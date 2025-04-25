package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderDeliveryDTO;
import org.example.entity.OrderDelivery;
import org.example.service.IOrderDelivery;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userorde")
@RequiredArgsConstructor
public class UserOrDeController {
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IOrderDelivery orderDelivery;
    @GetMapping("")
    public ResponseEntity<?> getOrDe()
    {
        int id = getIDAccountFromAuthService.common();
        List<OrderDelivery> orderDeliveryList = orderDelivery.findOrderDeliveryByAccountID(id);
        List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
        for (OrderDelivery orderDelivery1: orderDeliveryList)
        {
            OrderDeliveryDTO orderDeliveryDTO = new OrderDeliveryDTO();
            orderDeliveryDTO.setDateStart(orderDelivery1.getStart());
            orderDeliveryDTO.setDateEnd(orderDelivery1.getEnd());
            orderDeliveryDTO.setOrDeCondition(orderDelivery1.getOrDeCondition());
            orderDeliveryDTO.setOrderDeliveryID(orderDelivery1.getId());
            orderDeliveryDTO.setTotal(orderDelivery1.getTotal());
            orderDeliveryDTOS.add(orderDeliveryDTO);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orderDeliveryDTOS", orderDeliveryDTOS);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getOrDeDetail(@PathVariable int id)
//    {
//
//    }

}
