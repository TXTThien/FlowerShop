package org.example.controller.Shipper;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderShippingDTO;
import org.example.dto.ShippingRequest;
import org.example.entity.Account;
import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.Shipping;
import org.example.entity.enums.Status;
import org.example.repository.ShippingRepository;
import org.example.service.IAccountService;
import org.example.service.IOrderService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperReceiveController {
    private final IOrderService orderService;
    private final ShippingRepository shippingRepository;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> categories = orderService.findOrderNoShipping();
        Map<String, Object> response = new HashMap<>();
        response.put("orderList", categories);
        return ResponseEntity.ok(response);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        Order order= orderService.findOrderByOrderID(id);
        OrderShippingDTO orderShippingDTO = new OrderShippingDTO();

        orderShippingDTO.setOrderID(order.getOrderID());
        orderShippingDTO.setDate(order.getDate());
        orderShippingDTO.setIsPaid(order.getPaid());
        orderShippingDTO.setTotal(order.getTotalAmount());
        orderShippingDTO.setAddress(order.getDeliveryAddress());
        orderShippingDTO.setPhone(order.getPhoneNumber());
        orderShippingDTO.setName(order.getName());
        orderShippingDTO.setCondition(order.getCondition());
        orderShippingDTO.setNote(order.getNote());
        List<String> flowerNames = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getFlower().getName() + " " + orderDetail.getFlowerSize().getSizeName()) // Trích xuất tên hoa từ FlowerSize
                .collect(Collectors.toList());
        orderShippingDTO.setFlowerName(flowerNames.toArray(new String[0]));
        List<Integer> Quantity = order.getOrderDetails()
                .stream()
                .map(OrderDetail::getQuantity) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setQuantity(Quantity.toArray(new Integer[0]));
        List<Float> Length = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getLength()) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setLength(Length.toArray(new Float[0]));
        List<Float> Height = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getHigh()) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setHeight(Height.toArray(new Float[0]));
        List<Float> Width = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getWidth()) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setWidth(Width.toArray(new Float[0]));
        List<Float> Weight = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getWeight()) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setWeight(Weight.toArray(new Float[0]));
        List<String> Sizename = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getSizeName()) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setSizeName(Sizename.toArray(new String[0]));
        List<BigDecimal> Price = order.getOrderDetails()
                .stream()
                .map(orderDetail -> orderDetail.getFlowerSize().getPrice()) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setPrice(Price.toArray(new BigDecimal[0]));
        Map<String, Object> response = new HashMap<>();
        response.put("orderList", orderShippingDTO);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{orderID}/receive")
    public ResponseEntity<?> receiveOrders(@PathVariable int orderID, @RequestBody ShippingRequest shippingRequest) {
        try {
            int id = getIDAccountFromAuthService.common();
            Account account = accountService.getAccountById(id);
            if (account == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
            }

            Order order = orderService.findOrderByOrderID(orderID);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
            }

            Shipping shipping = new Shipping();
            shipping.setAccountID(account);
            if (shippingRequest.getNote() != null) {
                shipping.setNote(shippingRequest.getNote());
            }
            shipping.setStatus(Status.ENABLE);

            shippingRepository.save(shipping);
            order.setShipping(shipping);
            orderService.update(order);

            return ResponseEntity.ok("Order received successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


}
