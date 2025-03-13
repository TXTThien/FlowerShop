package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.example.controller.EmailController;
import org.example.controller.NotificationController;
import org.example.dto.PreorderList;
import org.example.entity.*;
import org.example.entity.enums.*;
import org.example.repository.OrderDetailRepository;
import org.example.repository.OrderRepository;
import org.example.repository.PreOrderRepository;
import org.example.repository.PreorderdetailRepository;
import org.example.service.IAccountService;
import org.example.service.IPreOrderService;
import org.example.service.IPreorderdetailService;
import org.example.service.ITypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/staff/preorder")
@RequiredArgsConstructor
public class StaffPreorderController {
    private final IPreorderdetailService preorderdetailService;
    private final PreorderdetailRepository preorderdetailRepository;
    private final PreOrderRepository preOrderRepository;
    private final IPreOrderService preOrderService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final IAccountService accountService;
    private final ITypeService typeService;
    private final NotificationController notificationController;
    private final EmailController emailController;
    @GetMapping
    public ResponseEntity<List<Preorder>> getAllCategories() {
        List<Preorder> categories = preOrderRepository.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPreOrder(@PathVariable int id){
        Preorder preorder = preOrderService.findPreorderByPreorderID(id);
        List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(preorder);
        Map<String, Object> response = new HashMap<>();
        response.put("preorders", preorder);
        response.put("preorderdetails", preorderdetails);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/total")
    public ResponseEntity<List<PreorderList>> getTotal() {
        List<PreorderList> preorderLists = new ArrayList<>();
        List<FlowerSize> flowerSizeList = preorderdetailService.findPreorderdetailOnce();

        for (int i = 0; i < flowerSizeList.size(); i++) {
            PreorderList preorderList = new PreorderList();
            preorderList.setId(i + 1);
            preorderList.setFlower(flowerSizeList.get(i).getFlower().getName());
            preorderList.setSize(flowerSizeList.get(i).getSizeName());
            preorderList.setFlowersizeid(flowerSizeList.get(i).getFlowerSizeID());
            preorderList.setNumber(preorderdetailService.countQuantityPreopen(flowerSizeList.get(i)));
            preorderLists.add(preorderList);
        }

        return ResponseEntity.ok(preorderLists);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editPreorder(@PathVariable int id, @RequestBody Preorder preorder) {
        System.out.println("id: " + id);
        Preorder existingPreorder = preOrderService.findPreorderByID(id);

        if (existingPreorder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đơn đặt trước không tồn tại.");
        }
        if (!Objects.equals(existingPreorder.getName(), preorder.getName()) || !Objects.equals(existingPreorder.getDeliveryAddress(), preorder.getDeliveryAddress()) || !Objects.equals(existingPreorder.getPhoneNumber(), preorder.getPhoneNumber()) || !Objects.equals(existingPreorder.getNote(), preorder.getNote()))
        {
            existingPreorder.setName(preorder.getName());
            existingPreorder.setNote(preorder.getNote());
            existingPreorder.setPhoneNumber(preorder.getPhoneNumber());
            existingPreorder.setDeliveryAddress(preorder.getDeliveryAddress());
            notificationController.preOrderInfomationNotification(existingPreorder.getId());
        }
        existingPreorder.setStatus(preorder.getStatus());

        if(existingPreorder.getPrecondition() != preorder.getPrecondition())
        {
            notificationController.preOrderConditionNotification(existingPreorder.getId());
            existingPreorder.setPrecondition(preorder.getPrecondition());
        }
        preOrderRepository.save(existingPreorder);
        return ResponseEntity.ok(existingPreorder);
    }

    @RequestMapping("/cancelable")
    public void cancelable(){
        List<Preorder> preorders = preOrderService.findPreorderWatingOrdering();
        int count = 0;
        for (Preorder preorder : preorders) {
            if (preorder.getPrecondition() == Precondition.Ordering)
                count++;
        }
        if (count == preorders.size())
        {
            for (Preorder preorder : preorders) {
                preorder.setPrecondition(Precondition.Waiting);
                preOrderRepository.save(preorder);
                notificationController.preOrderConditionNotification(preorder.getId());
            }
        }
        else
        {
            for (Preorder preorder : preorders) {
                preorder.setPrecondition(Precondition.Ordering);
                preOrderRepository.save(preorder);
                notificationController.preOrderConditionNotification(preorder.getId());

            }
        }
    }
    @RequestMapping("/cancelable/{id}")
    public void cancelableID(@PathVariable int id){
        Preorder preorder = preOrderService.findPreorderByPreorderID(id);
        if (preorder.getPrecondition() == Precondition.Waiting)
            preorder.setPrecondition(Precondition.Ordering);
        else preorder.setPrecondition(Precondition.Waiting);
        preOrderRepository.save(preorder);
        notificationController.preOrderConditionNotification(preorder.getId());

    }
    @RequestMapping("/complete")
    public void completePre(){
        List<Preorder> categories = preOrderRepository.findAll();
        for (Preorder category : categories) {
            CompletePreorder(category);
        }
    }
    @RequestMapping("/complete/{id}")
    public void completePre(@PathVariable int id){
        Preorder preorder = preOrderService.findPreorderByPreorderID(id);
        CompletePreorder(preorder);
    }
    public void CompletePreorder(Preorder preorder) {
        List<Preorderdetail> preorderdetails = preorderdetailService.findPreorderdetailByPreorder(preorder);
        Order newOrder = new Order();
        newOrder.setAccountID(preorder.getAccount());
        newOrder.setStatus(Status.ENABLE);
        newOrder.setDate(preorder.getDate());
        newOrder.setName(preorder.getName());
        newOrder.setNote(preorder.getNote());
        newOrder.setTotalAmount(preorder.getTotalAmount());
        newOrder.setPhoneNumber(preorder.getPhoneNumber());
        newOrder.setDeliveryAddress(preorder.getDeliveryAddress());
        newOrder.setVnp_TransactionNo(preorder.getVnp_TransactionNo());
        newOrder.setCondition(Condition.In_Transit);
        BigDecimal total = BigDecimal.ZERO;
        for (Preorderdetail detail : preorderdetails) {
            BigDecimal remainingAmount = detail.getPrice().subtract(detail.getPaid());
            if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
                total = total.add(remainingAmount);
            }
        }
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            newOrder.setPaid(IsPaid.Yes);
            newOrder.setHadpaid(preorder.getTotalAmount());
        } else {
            newOrder.setPaid(IsPaid.No);
            newOrder.setHadpaid(preorder.getTotalAmount().subtract(total));
        }
        orderRepository.save(newOrder);
        for (Preorderdetail detail : preorderdetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderID(newOrder);
            orderDetail.setPaid(detail.getPaid());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setFlowerSize(detail.getFlowerSize());
            orderDetail.setStatus(Status.ENABLE);
            orderDetail.setPrice(detail.getPrice());
            orderDetailRepository.save(orderDetail);
        }
        preorder.setPrecondition(Precondition.Success);
        preOrderRepository.save(preorder);
        notificationController.preOrderSuccessNotification(preorder.getId(),newOrder.getOrderID());
        emailController.PreorderToOrder(preorder,newOrder);
    }

}
