package org.example.controller.Shipper;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.example.auth.ChangePassword;
import org.example.dto.OrderShippingDTO;
import org.example.dto.ShipperNoteImage;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.ShippingRepository;
import org.example.service.IAccountService;
import org.example.service.IOrderDetailService;
import org.example.service.IOrderService;
import org.example.service.ITypeService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shipperaccount")
@RequiredArgsConstructor
public class ShipperAccountController {
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    private final ShippingRepository shippingRepository;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ITypeService typeService;
    @GetMapping("")
    public ResponseEntity<Account> getAccountInfo() {
        int idAccount = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(idAccount);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updateinfo")
    public ResponseEntity<String> updateAccountByAccountID(
            @RequestBody Account updateAccountRequest) {
        int idAccount = getIDAccountFromAuthService.common();


        Account currentAccount = accountService.getAccountById(idAccount);

        if (currentAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại.");
        }

        if (updateAccountRequest.getName() != null) {
            currentAccount.setName(updateAccountRequest.getName());
        }
        if (updateAccountRequest.getPhoneNumber() != null) {
            currentAccount.setPhoneNumber(updateAccountRequest.getPhoneNumber());
        }
        if (updateAccountRequest.getAddress() != null) {
            currentAccount.setAddress(updateAccountRequest.getAddress());
        }
        if (updateAccountRequest.getEmail() != null) {
            currentAccount.setEmail(updateAccountRequest.getEmail());
        }
        if (updateAccountRequest.getAvatar() != null) {
            currentAccount.setAvatar(updateAccountRequest.getAvatar());
        }
        accountService.updateAccountInfo(currentAccount);

        return ResponseEntity.ok("Cập nhật thông tin tài khoản thành công.");
    }

    @PutMapping("/changepassword")
    public ResponseEntity<String> updateChangePassword(
            @RequestBody ChangePassword changePassword) {

        int idAccount = getIDAccountFromAuthService.common();
        String newpass = changePassword.getNewpass();
        String curpass = changePassword.getCurpass();

        Account account = accountService.getAccountById(idAccount);

        if (!passwordEncoder.matches(curpass, account.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu hiện tại không đúng.");
        }

        String updatepass = passwordEncoder.encode(newpass);
        accountRepository.updatePassword(updatepass, idAccount);

        return ResponseEntity.ok("Đổi mật khẩu thành công.");
    }


    @GetMapping("/ordership")
    public ResponseEntity<?> getOrderToShip(){
        int id = getIDAccountFromAuthService.common();
        List<Order> orderList = orderService.findOrderByShipperID(id);
        Map<String, Object> response = new HashMap<>();
        response.put("orderList", orderList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ordership/{orderid}")
    public ResponseEntity<?> getOrderID(@PathVariable int orderid){
        int id = getIDAccountFromAuthService.common();
        Order order = orderService.findOrderByOrderID(orderid);
        OrderShippingDTO orderShippingDTO = new OrderShippingDTO();
        orderShippingDTO.setShipperName(order.getShipping().getAccountID().getName());
        orderShippingDTO.setHadPaid(order.getHadpaid());
        orderShippingDTO.setOrderID(order.getOrderID());
        orderShippingDTO.setShippingID(order.getShipping().getShippingID());
        orderShippingDTO.setShipperID(order.getShipping().getAccountID().getAccountID());
        orderShippingDTO.setDate(order.getDate());
        if (order.getShipping().getCompleteDate() !=null){
            orderShippingDTO.setShipEnd(order.getShipping().getCompleteDate());
        }
        if (order.getShipping().getStartDate() !=null){
            orderShippingDTO.setShipStart(order.getShipping().getStartDate());
        }
        if (order.getShipping().getNote() !=null){
            orderShippingDTO.setShipperNote(order.getShipping().getNote());
        }
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
                .map(OrderDetail::getPrice) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setPrice(Price.toArray(new BigDecimal[0]));
        List<BigDecimal> Paid = order.getOrderDetails()
                .stream()
                .map(OrderDetail::getPaid) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setPaid(Paid.toArray(new BigDecimal[0]));
        Map<String, Object> response = new HashMap<>();
        response.put("orderShippingDTO", orderShippingDTO);

        return ResponseEntity.ok(response);
    }
    @RequestMapping("/haveship/{orderid}/start")
    public ResponseEntity<String> getStart(@PathVariable int orderid, @RequestParam(required = false) String shippernote){
        Order order = orderService.findOrderByOrderID(orderid);
        Shipping shipping = order.getShipping();
        if (order.getCondition() == Condition.In_Transit)
        {
            order.setCondition(Condition.Shipper_Delivering);
            shipping.setStartDate(LocalDateTime.now());
            if (shippernote!=null)
            {
                shipping.setNote(shippernote);
            }
            shippingRepository.save(shipping);
            orderService.update(order);
            return ResponseEntity.ok("Bắt đầu giao!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đơn hàng không trong trạng thái vận chuyển.");
    }
    @RequestMapping("/haveship/{orderid}/fail")
    public ResponseEntity<String> getFail(@PathVariable int orderid, @RequestBody(required = false) ShipperNoteImage shipperNoteImage){
        Order order = orderService.findOrderByOrderID(orderid);
        Shipping shipping = order.getShipping();
        if (order.getCondition() == Condition.Shipper_Delivering)
        {
            order.setCondition(Condition.First_Attempt_Failed);
            if (shipperNoteImage.getImage()!=null)
            {
                order.setPicture(shipperNoteImage.getImage());
            }
            if (shipperNoteImage.getText()!=null)
            {
                order.setText(shipperNoteImage.getText());
            }
            else if (shipperNoteImage.getText() == null)
            {
                order.setText("Giao thất bại lần 1");
            }
            shippingRepository.save(shipping);
            orderService.update(order);
            return ResponseEntity.ok("Thất bại lần 1!");
        }
        else if (order.getCondition() == Condition.First_Attempt_Failed)
        {
            order.setCondition(Condition.Second_Attempt_Failed);
            if (shipperNoteImage.getImage()!=null)
            {
                order.setPicture(shipperNoteImage.getImage());
            }
            if (shipperNoteImage.getText()!=null)
            {
                order.setText(shipperNoteImage.getText());
            }
            else if (shipperNoteImage.getText() == null)
            {
                order.setText("Giao thất bại lần 2");
            }
            shippingRepository.save(shipping);
            orderService.update(order);
            return ResponseEntity.ok("Thất bại lần 2!");
        }
        else if (order.getCondition() == Condition.Second_Attempt_Failed)
        {
            order.setCondition(Condition.Third_Attempt_Failed);
            if (shipperNoteImage.getImage()!=null)
            {
                order.setPicture(shipperNoteImage.getImage());
            }
            if (shipperNoteImage.getText()!=null)
            {
                order.setText(shipperNoteImage.getText());
            }
            else if (shipperNoteImage.getText() == null)
            {
                order.setText("Giao thất bại lần 3");
            }
            shippingRepository.save(shipping);
            orderService.update(order);
            return ResponseEntity.ok("Thất bại lần 3!");
        }
        else if (order.getCondition() == Condition.Third_Attempt_Failed)
        {
            order.setCondition(Condition.Return_to_shop);
            if (shipperNoteImage.getImage()!=null)
            {
                order.setPicture(shipperNoteImage.getImage());
            }
            if (shipperNoteImage.getText()!=null)
            {
                order.setText(shipperNoteImage.getText());
            }
            else if (shipperNoteImage.getText() == null)
            {
                order.setText("Đơn hàng giao thất bại");
            }
            shipping.setCompleteDate(LocalDateTime.now());
            shippingRepository.save(shipping);
            orderService.update(order);
            return ResponseEntity.ok("Trả về shop!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đơn hàng không trong trạng thái vận chuyển.");
    }
    @GetMapping("/haveship")
    public ResponseEntity<?> getOrderHaveShip(){
        int id = getIDAccountFromAuthService.common();
        List<Order> orderList = orderService.findOrderByShipperIDAndCondition(id);
        Map<String, Object> response = new HashMap<>();
        response.put("orderList", orderList);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/haveship/{orderid}")
    public ResponseEntity<?> getHaveShipOrderID(@PathVariable int orderid){
        int id = getIDAccountFromAuthService.common();
        Order order = orderService.findOrderByOrderID(orderid);
        OrderShippingDTO orderShippingDTO = new OrderShippingDTO();
        orderShippingDTO.setHadPaid(order.getHadpaid());
        orderShippingDTO.setOrderID(order.getOrderID());
        orderShippingDTO.setShippingID(order.getShipping().getShippingID());
        orderShippingDTO.setShipperID(order.getShipping().getAccountID().getAccountID());
        orderShippingDTO.setDate(order.getDate());
        if (order.getShipping().getCompleteDate() !=null){
            orderShippingDTO.setShipEnd(order.getShipping().getCompleteDate());
        }
        if (order.getShipping().getStartDate() !=null){
            orderShippingDTO.setShipStart(order.getShipping().getStartDate());
        }
        if (order.getShipping().getNote() !=null){
            orderShippingDTO.setShipperNote(order.getShipping().getNote());
        }
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
        List<BigDecimal> Price = order.getOrderDetails()
                .stream()
                .map(OrderDetail::getPrice) // Trích xuất tên hoa từ FlowerSize
                .toList();
        orderShippingDTO.setPrice(Price.toArray(new BigDecimal[0]));
        Map<String, Object> response = new HashMap<>();
        response.put("orderShippingDTO", orderShippingDTO);
        return ResponseEntity.ok(response);
    }
    @RequestMapping("/haveship/{orderid}/success")
    public ResponseEntity<String> getSuccess(@PathVariable int orderid, @RequestBody(required = false) ShipperNoteImage shipperNoteImage){
        Order order = orderService.findOrderByOrderID(orderid);
        Account account = order.getAccountID();
        Shipping shipping = order.getShipping();
        String success= "Giao thành công";
        BigDecimal consume = account.getConsume();
        if (order.getCondition() == Condition.Shipper_Delivering||order.getCondition() == Condition.First_Attempt_Failed||order.getCondition() == Condition.Second_Attempt_Failed||order.getCondition() == Condition.Third_Attempt_Failed)
        {
            order.setCondition(Condition.Delivered_Successfully);
            if(order.getPaid()==IsPaid.No)
            {
                order.setHadpaid(order.getTotalAmount());
                List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByOrderID(orderid);
                for (int i = 0;i<orderDetails.size();i++)
                {
                    orderDetails.get(i).setPaid(orderDetails.get(i).getPrice());
                }
                order.setPaid(IsPaid.Yes);
                BigDecimal total = consume.add(order.getTotalAmount().subtract(order.getHadpaid()));
                account.setConsume(total);
                List<Type> types = typeService.findAllOrderByMinConsumeAsc();
                Type appropriateType = null;
                for (Type type : types) {
                    if (total.compareTo(type.getMinConsume()) >= 0) {
                        account.setType(type); // Cập nhật Type
                    } else {
                        break; // Dừng kiểm tra nếu consume không đạt mức tiếp theo
                    }
                }
            }
            shipping.setCompleteDate(LocalDateTime.now());
            if (shipperNoteImage.getImage()!=null)
            {
                order.setPicture(shipperNoteImage.getImage());
            }
            if (shipperNoteImage.getText()!=null)
            {
                order.setText(shipperNoteImage.getText());
            }
            else if (shipperNoteImage.getText() == null)
            {
                order.setText(success);
            }
            accountService.save(account);
            shippingRepository.save(shipping);
            orderService.update(order);
            return ResponseEntity.ok("Giao thành công!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đơn hàng không trong trạng thái vận chuyển.");
    }
}