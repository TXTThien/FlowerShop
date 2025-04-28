package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.InfoOrderDelivery;
import org.example.dto.OrDeDetailDTO;
import org.example.dto.OrderDeliveryDTO;
import org.example.dto.RefundRequest;
import org.example.entity.*;
import org.example.entity.enums.OrDeCondition;
import org.example.entity.enums.Status;
import org.example.repository.OrderDeliveryRepository;
import org.example.repository.RefundResponsitory;
import org.example.service.IAccountService;
import org.example.service.IOrderDelivery;
import org.example.service.IOrderDeliveryDetail;
import org.example.service.IOrderService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/userorde")
@RequiredArgsConstructor
public class UserOrDeController {
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IOrderDelivery orderDelivery;
    private final IOrderDeliveryDetail orderDeliveryDetail;
    private final IOrderService orderService;
    private final IAccountService accountService;
    private final RefundResponsitory refundResponsitory;
    private final OrderDeliveryRepository orderDeliveryRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrDeDetail(@PathVariable int id)
    {
        OrderDelivery orderDelivery1 = orderDelivery.findOrderDelivery(id);
        InfoOrderDelivery orderDeliveryDTO = new InfoOrderDelivery();
        orderDeliveryDTO.setAddress(orderDelivery1.getAddress());
        orderDeliveryDTO.setNote(orderDelivery1.getNote());
        orderDeliveryDTO.setName(orderDelivery1.getName());
        orderDeliveryDTO.setPhoneNumber(orderDelivery1.getPhoneNumber());
        orderDeliveryDTO.setOrDeCondition(orderDelivery1.getOrDeCondition());
        orderDeliveryDTO.setId(orderDelivery1.getId());
        orderDeliveryDTO.setOrDeType(orderDelivery1.getOrderDeliveryType().getType());
        orderDeliveryDTO.setDays(orderDelivery1.getOrderDeliveryType().getDays());
        orderDeliveryDTO.setCostperday(orderDelivery1.getOrderDeliveryType().getCost());
        orderDeliveryDTO.setStart(orderDelivery1.getStart());
        if (orderDelivery1.getEnd() != null)
            orderDeliveryDTO.setEnd(orderDelivery1.getEnd());
        orderDeliveryDTO.setTotal(orderDelivery1.getTotal());
        orderDeliveryDTO.setDeliverper(orderDelivery1.getDeliverper());
        List<Order> orders = orderService.findOrdersByOrDeIDAndCondition(id);
        orderDeliveryDTO.setNumberDelivered(orders.size());

        List<OrderDeliveryDetail> orderDeliveryDetailList = orderDeliveryDetail.findOrDeDetailByOrDeID(id);
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

    @RequestMapping("/{id}/refund")
    public ResponseEntity<?> makeRefund (@PathVariable int id, @RequestBody RefundRequest refundRequest)
    {
        Account account = accountService.getAccountById(getIDAccountFromAuthService.common());
        OrderDelivery orderDelivery1 = orderDelivery.findOrderDelivery(id);

        if (account == orderDelivery1.getAccountID())
        {
            if (orderDelivery1.getOrDeCondition() == OrDeCondition.REFUND || orderDelivery1.getOrDeCondition() == null){
                if (Objects.equals(orderDelivery1.getVnp_TransactionNo(), refundRequest.getVnp_TransactionNo()))
                {
                    Refund refund = new Refund();
                    refund.setDate(LocalDateTime.now());
                    refund.setStatus(Status.ENABLE);
                    refund.setOrderdeliveryid(orderDelivery1);
                    refund.setBank(refundRequest.getBank());
                    refund.setNumber(refundRequest.getNumber());
                    refundResponsitory.save(refund);

                    orderDelivery1.setOrDeCondition(OrDeCondition.REFUND_IS_WAITING);
                    orderDeliveryRepository.save(orderDelivery1);

                    return ResponseEntity.ok("Gửi yêu cầu hoàn tiền thành công");
                }
                else return ResponseEntity.badRequest().body("Sai mã VNP Transaction");
            }
            else return ResponseEntity.badRequest().body("Điều kiện không phù hợp");
        }
        else return ResponseEntity.badRequest().body("Không thể tiến hành thao tác trên đơn này");
    }


}
