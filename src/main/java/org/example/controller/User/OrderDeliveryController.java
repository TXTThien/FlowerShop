package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.controller.EmailController;
import org.example.controller.NotificationController;
import org.example.dto.*;
import org.example.entity.*;
import org.example.entity.Type;
import org.example.entity.enums.*;
import org.example.repository.OrderDeliveryDetailRepository;
import org.example.repository.OrderDeliveryRepository;
import org.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orderdelivery")
@RequiredArgsConstructor
public class OrderDeliveryController {
    private final IOrderDeliveryType orderDeliveryType;
    private final IFlowerService flowerService;
    private final IFlowerSizeService flowerSizeService;
    private final IAccountService accountService;
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final OrderDeliveryDetailRepository orderDeliveryDetailRepository;
    private final ITypeService typeService;
    private final EmailController emailController;
    private final NotificationController notificationController;

    @GetMapping("")
    private ResponseEntity<?> getInfoOrderDelivery() {
        List<OrderDeliveryType> orderDeliveryTypeList = orderDeliveryType.findAllEnable();
        List<Deliverper> deliverpers = Arrays.asList(Deliverper.values());
        List<Flower> flowers = flowerService.findAllEnable();
        List<FlowerInfo> allFlowers = flowers.stream()
                .map(flower -> {
                    FlowerInfo flowerInfo = new FlowerInfo();
                    flowerInfo.setId(flower.getFlowerID());
                    flowerInfo.setName(flower.getName());
                    flowerInfo.setImage(flower.getImage());

                    List<FlowerSizeDTO> flowerSizeDTOS = flowerSizeService.findFlowerSizeByProductID(flower.getFlowerID()).stream()
                            .map(flowerSize -> new FlowerSizeDTO(
                                    flowerSize.getFlowerSizeID(),
                                    flowerSize.getSizeName(),
                                    flowerSize.getFlower().getImage(),
                                    flowerSize.getPrice()
                            ))
                            .collect(Collectors.toList());

                    flowerInfo.setFlowerSizeDTOS(flowerSizeDTOS);
                    return flowerInfo;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("orderDeliveryTypeList", orderDeliveryTypeList);
        response.put("allFlowers", allFlowers);
        response.put("deliverpers", deliverpers);

        // Trả về response
        return ResponseEntity.ok(response);
    }


    public BigDecimal sum(BigDecimal[] prices) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal price : prices) {
            if (price != null) {
                total = total.add(price);
            }
        }
        return total;
    }

    public void createOrderDelivery(BigDecimal total, OrderDeliveryDTO orderDeliveryDTO, int accountId, String vnp_TransactionNo) {
        try {
            Account account = accountService.getAccountById(accountId);
            OrderDelivery orderDelivery = new OrderDelivery();
            orderDelivery.setAccountID(account);
            orderDelivery.setPhoneNumber(orderDeliveryDTO.getPhone());
            orderDelivery.setAddress(orderDeliveryDTO.getAddress());
            orderDelivery.setNote(orderDeliveryDTO.getNote());
            orderDelivery.setName(orderDeliveryDTO.getName());
            orderDelivery.setOrderDeliveryType(orderDeliveryType.findTypeByIDEnable(orderDeliveryDTO.getOrderDeliveryTypeID()));
            orderDelivery.setStart(orderDeliveryDTO.getDateStart());
            System.out.println("Tới dòng này: 1");
            System.out.println("total: "+total);

            orderDelivery.setTotal(total);
            orderDelivery.setDeliverper(Deliverper.valueOf(orderDeliveryDTO.getDeliverper()));

            orderDelivery.setStatus(Status.ENABLE);

            orderDelivery.setVnp_TransactionNo(vnp_TransactionNo);
            System.out.println("Tới dòng này: 2");

            orderDeliveryRepository.save(orderDelivery);
            System.out.println("Tới dòng này: 3");
            List<OrderDeliveryDetail> orderDeliveryDetailList = new ArrayList<>();
            for (FlowerChoose flowerChoose : orderDeliveryDTO.getFlowerChooses()) {
                OrderDeliveryDetail orderDeliveryDetail = new OrderDeliveryDetail();
                orderDeliveryDetail.setOrderDelivery(orderDelivery);
                orderDeliveryDetail.setQuantity(flowerChoose.getQuantity());
                orderDeliveryDetail.setFlowerSize(flowerSizeService.findFlowerSizeByID(flowerChoose.getFlowersizeid()));
                orderDeliveryDetailList.add(orderDeliveryDetail);
            }
            orderDeliveryDetailRepository.saveAll(orderDeliveryDetailList);
            System.out.println("Tới dòng này: 4");

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
            System.out.println("Tới dòng này: 5");

            accountService.save(account);
            System.out.println("Tới dòng này: 6");

            emailController.OrDeSuccess(orderDelivery, accountId);
            System.out.println("Tới dòng này: 7");

            notificationController.OrDeNotificationCreate(orderDelivery.getId());

            ResponseEntity.status(HttpStatus.CREATED)
                    .body("Order Delivery purchased successfully.");
        } catch (
                Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating Order Delivery: " + e.getMessage());
        }
    }
}
