package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.controller.NotificationController;
import org.example.dto.InfoOrderDelivery;
import org.example.dto.OrDeDetailDTO;
import org.example.entity.*;
import org.example.entity.enums.*;
import org.example.repository.OrderDeliveryRepository;
import org.example.repository.OrderDetailRepository;
import org.example.repository.OrderRepository;
import org.example.service.IOrderDelivery;
import org.example.service.IOrderDeliveryDetail;
import org.example.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/orde")
@RequiredArgsConstructor
public class AdminOrDeController {
    private final IOrderDelivery orderDeliveryService;
    private final IOrderDeliveryDetail orderDeliveryDetailService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final IOrderService orderService;
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final NotificationController notificationController;
    @GetMapping("")
    public ResponseEntity<?> getOrDeInfo ()
    {
        List<OrderDelivery> orderDeliveryList = orderDeliveryService.findAllOrDeStaffAdmin();
        List<OrderDelivery> orderDeliveryCancel = orderDeliveryService.findOrDeByCondition(OrDeCondition.CANCEL_REQUEST_IS_WAITING);
        List<OrderDelivery> orderDeliveryOnGoing = orderDeliveryService.findOrDeByCondition(OrDeCondition.ONGOING);
        List<OrderDelivery> newOrDelivery = orderDeliveryService.findOrDeByCondition(null);

        List<OrderDelivery> haveDeli = new ArrayList<>();

        for (OrderDelivery orderDelivery1 : orderDeliveryOnGoing) {
            List<Order> orders = orderService.findOrdersByOrDeIDEnable(orderDelivery1.getId());

            Deliverper dayper = orderDelivery1.getDeliverper();
            LocalDate nowDate = LocalDate.now();
            LocalDate startDate = orderDelivery1.getStart().toLocalDate();
            long daysBetween;
            if (!orders.isEmpty()) {
                Order lastOrder = orders.get(orders.size() - 1);
                daysBetween = ChronoUnit.DAYS.between(lastOrder.getDate().toLocalDate(), nowDate);
            }
            else
                daysBetween = ChronoUnit.DAYS.between(startDate, nowDate);
            long devper;
            switch (dayper) {
                case every_day -> devper = 1;
                case two_day -> devper = 2;
                case three_day -> devper = 3;
                default -> devper = 1; // fallback an toàn
            }
            boolean delivered = false;

            if (!orders.isEmpty()) {
                Order lastOrder = orders.get(orders.size() - 1);
                if (Objects.equals(lastOrder.getDate().toLocalDate(), LocalDate.now())) {
                    delivered = true;
                }
            }

            if (orderDelivery1.getStart().isBefore(LocalDateTime.now()) && orderDelivery1.getCondition() == OrDeCondition.ONGOING && daysBetween >= 0 && (daysBetween % devper == 0 || daysBetween / devper >= 1)
                    && orders.size() < orderDelivery1.getOrderDeliveryType().getDays() && !delivered) {
                haveDeli.add(orderDelivery1);
            }

        }

        Map<String, Object> response = new HashMap<>();
        response.put("NewOrDe", newOrDelivery);
        response.put("AllOrDe", orderDeliveryList);
        response.put("HaveDeli", haveDeli);
        response.put("CancelReq", orderDeliveryCancel);
        return ResponseEntity.ok(response);
    }
    @RequestMapping("/{id}/acceptNew")
    public void acceptNewOrDe (@PathVariable int id)
    {
        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        if (orderDelivery1.getCondition() == null)
        {
            orderDelivery1.setCondition(OrDeCondition.ONGOING);
            notificationController.NewOrDeAcceptNotification(id);
        }
    }
    @RequestMapping("/{id}/declineNew")
    public void declineNewOrDe (@PathVariable int id)
    {
        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        if (orderDelivery1.getCondition() == null)
        {
            orderDelivery1.setCondition(OrDeCondition.REFUND);
            notificationController.NewOrDeDeclineNotification(id);
        }
    }
    @RequestMapping("/{id}/deli")
    public ResponseEntity<?> deliNow (@PathVariable int id){

        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        if (orderDelivery1.getCondition() == OrDeCondition.ONGOING)
        {
            Order order = new Order();
            order.setOrderDelivery(orderDelivery1);
            order.setTime(LocalDateTime.now());
            order.setPaid(IsPaid.Yes);
            order.setAccountID(orderDelivery1.getAccountID());
            order.setStatus(Status.ENABLE);
            order.setTotalAmount(
                    orderDelivery1.getTotal()
                            .divide(BigDecimal.valueOf(orderDelivery1.getOrderDeliveryType().getDays()), RoundingMode.HALF_UP)
            );
            order.setDate(LocalDateTime.now());
            order.setHadpaid(order.getTotalAmount());
            order.setDeliveryAddress(orderDelivery1.getAddress());
            order.setName(orderDelivery1.getName());
            order.setNote(orderDelivery1.getNote());
            order.setPhoneNumber(orderDelivery1.getPhoneNumber());
            order.setCondition(Condition.In_Transit);
            orderRepository.save(order);

            List<OrderDeliveryDetail> orderDeliveryDetailList = orderDeliveryDetailService.findOrDeDetailByOrDeID(id);
            for (OrderDeliveryDetail orderDeliveryDetail: orderDeliveryDetailList)
            {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderID(order);
                orderDetail.setStatus(Status.ENABLE);
                orderDetail.setQuantity(orderDeliveryDetail.getQuantity());
                orderDetail.setFlowerSize(orderDeliveryDetail.getFlowerSize());
                orderDetail.setPrice(orderDeliveryDetail.getFlowerSize().getPrice());
                orderDetail.setPaid(
                        orderDeliveryDetail.getFlowerSize().getPrice()
                                .multiply(BigDecimal.valueOf(orderDeliveryDetail.getQuantity()))
                );
                orderDetailRepository.save(orderDetail);
            }
            notificationController.OrDeSuccessNotification(id,order);
            return ResponseEntity.ok("Order  created successfully");
        }
        else
            return ResponseEntity.badRequest().body("Order  created fail");
    }

    @RequestMapping("/{id}/acceptCancelRequest")
    public ResponseEntity<?> acceptRequest (@PathVariable int id){
        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        if (orderDelivery1.getCondition() == OrDeCondition.CANCEL_REQUEST_IS_WAITING)
        {
            orderDelivery1.setCondition(OrDeCondition.REFUND);

            List<Order> orderSuccess = orderService.findOrdersByOrDeIDAndCondition(id, Condition.Delivered_Successfully);
            List<Order> orderFail = orderService.findOrdersByOrDeIDAndCondition(id, Condition.Return_to_shop);
            List<Order> orderList = new ArrayList<>();
            orderList.addAll(orderSuccess);
            orderList.addAll(orderFail);
            BigDecimal perMoney = orderDelivery1.getTotal().divide(
                    BigDecimal.valueOf(orderDelivery1.getOrderDeliveryType().getDays()),RoundingMode.HALF_UP);
            BigDecimal refundMoney = perMoney.multiply(BigDecimal.valueOf(orderDelivery1.getOrderDeliveryType().getDays() - orderList.size()));
            orderDelivery1.setRefund(refundMoney);
            orderDeliveryRepository.save(orderDelivery1);
            notificationController.OrDeCancelSuccessNotification(id);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body("Cannot Accept");
    }

    @RequestMapping("/{id}/declineCancelRequest")
    public ResponseEntity<?> declineRequest (@PathVariable int id){
        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        if (orderDelivery1.getCondition() == OrDeCondition.CANCEL_REQUEST_IS_WAITING)
        {
            orderDelivery1.setCondition(OrDeCondition.ONGOING);
            orderDeliveryRepository.save(orderDelivery1);
            notificationController.OrDeCancelFailNotification(id);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body("Cannot decline");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailInfoOrDe (@PathVariable int id){
        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        InfoOrderDelivery orderDeliveryDTO = new InfoOrderDelivery();
        orderDeliveryDTO.setAddress(orderDelivery1.getAddress());
        orderDeliveryDTO.setNote(orderDelivery1.getNote());
        orderDeliveryDTO.setName(orderDelivery1.getName());
        orderDeliveryDTO.setPhoneNumber(orderDelivery1.getPhoneNumber());
        orderDeliveryDTO.setOrDeCondition(orderDelivery1.getCondition());
        orderDeliveryDTO.setId(orderDelivery1.getId());
        orderDeliveryDTO.setOrDeType(orderDelivery1.getOrderDeliveryType().getType());
        orderDeliveryDTO.setDays(String.valueOf(orderDelivery1.getOrderDeliveryType().getDays()));
        orderDeliveryDTO.setCostperday(orderDelivery1.getOrderDeliveryType().getCost());
        orderDeliveryDTO.setStart(orderDelivery1.getStart());
        if (orderDelivery1.getEnd() != null)
            orderDeliveryDTO.setEnd(orderDelivery1.getEnd());
        orderDeliveryDTO.setTotal(orderDelivery1.getTotal());
        orderDeliveryDTO.setDeliverper(orderDelivery1.getDeliverper());
        List<Order> orders = orderService.findOrdersByOrDeID(id);
        orderDeliveryDTO.setNumberDelivered(orders.size());

        long days = ChronoUnit.DAYS.between(orderDelivery1.getStart(), LocalDateTime.now());
        Deliverper dayper = orderDelivery1.getDeliverper();
        long devper;

        switch (dayper) {
            case every_day -> devper = 1;
            case two_day -> devper = 2;
            case three_day -> devper = 3;
            default -> devper = 1; // fallback an toàn
        }

        if (days % devper == 0 && orderDelivery1.getCondition() == OrDeCondition.ONGOING) {
            orderDeliveryDTO.setDeliver(Boolean.TRUE);
        }
        else
            orderDeliveryDTO.setDeliver(Boolean.FALSE);

        List<OrderDeliveryDetail> orderDeliveryDetailList = orderDeliveryDetailService.findOrDeDetailByOrDeID(id);
        List<OrDeDetailDTO> orDeDetailDTOS = new ArrayList<>();
        for (int i = 0; i<orderDeliveryDetailList.size();i++)
        {
            FlowerSize flowerSize = orderDeliveryDetailList.get(i).getFlowerSize();
            OrDeDetailDTO orDeDetailDTO = new OrDeDetailDTO();
            orDeDetailDTO.setId(i+1);
            orDeDetailDTO.setCount(orderDeliveryDetailList.get(i).getQuantity());
            orDeDetailDTO.setFlowername(flowerSize.getFlower().getName());
            orDeDetailDTO.setLength(flowerSize.getLength());
            orDeDetailDTO.setHeight(flowerSize.getHigh());
            orDeDetailDTO.setWidth(flowerSize.getWidth());
            orDeDetailDTO.setWeight(flowerSize.getWeight());
            orDeDetailDTO.setPrice(flowerSize.getPrice());
            orDeDetailDTO.setOrDeID(orderDeliveryDetailList.get(i).getOrderDelivery().getId());
            orDeDetailDTO.setFlowersize(flowerSize.getSizeName());
            orDeDetailDTOS.add(orDeDetailDTO);
        }
        orderDeliveryDTO.setOrDeDetailDTOS(orDeDetailDTOS);
        Map<String, Object> response = new HashMap<>();
        response.put("orderDeliveryDTO", orderDeliveryDTO);
        return ResponseEntity.ok(response);

    }
}
