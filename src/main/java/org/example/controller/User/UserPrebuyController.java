package org.example.controller.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.controller.EmailController;
import org.example.controller.NotificationController;
import org.example.dto.BuyInfo;
import org.example.dto.CartDTO;
import org.example.dto.CartUpdateRequest;
import org.example.entity.*;
import org.example.entity.Type;
import org.example.entity.enums.*;
import org.example.repository.*;
import org.example.repository.OrderRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prebuy")
@RequiredArgsConstructor
public class UserPrebuyController {
    private final ICartService cartService;
    private final IPrebuyService prebuyService;
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;
    private final IOrderService orderService;
    private final OrderDetailRepository orderDetailRepository;
    private final IFlowerSizeService flowerSizeService;
    private final IAccountService accountService;
    private final ITypeService typeService;
    private final AccountRepository accountRepository;
    private final PreorderdetailRepository preorderdetailRepository;
    private final PreOrderRepository preOrderRepository;
    private final EmailController emailController;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final SimpMessagingTemplate messagingTemplate;
    private final IEventFlowerService eventFlowerService;
    private final NotificationController notificationController;
    private final IDiscountService discountService;
    public void notifyCartUpdate(int accountId, int newCartCount) {
        Map<String, Object> message = new HashMap<>();
        if (accountId == -1)
        {
            newCartCount = 0;
        }
        message.put("accountId", accountId);
        message.put("cartCount", newCartCount);
        messagingTemplate.convertAndSend("/topic/cart-update", message);
    }
    public int cartCount (int accountid){
        List<Cart> cartorderList = cartService.findCartsByAccountID(accountid, org.example.entity.enums.Type.Order );
        List<Cart> cartpreorderList = cartService.findCartsByAccountID(accountid, org.example.entity.enums.Type.Preorder );
        return cartpreorderList.size() + cartorderList.size();
    }
    @GetMapping("")
    public ResponseEntity<?> getCart(HttpServletRequest request) {
        int id = getIDAccountFromAuthService.common();

        // Fetch account details
        Account account = accountRepository.findAccountByAccountID(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found for ID: " + id);
        }

        // Fetch cart details
        List<Cart> cartorderList = cartService.findCartsByAccountID(id, org.example.entity.enums.Type.Order );
        List<Cart> cartpreorderList = cartService.findCartsByAccountID(id, org.example.entity.enums.Type.Preorder );


        // Fetch discounts
        List<Discount> discounts = discountService.findAllEnable();

        // Set account ID in session
        request.getSession().setAttribute("accountID", id);

        // Build cartDTO list
        List<CartDTO> cartOrderList = cartorderList.stream().map(cart -> {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCartID(cart.getCartID());
            cartDTO.setSizeChoose(cart.getFlowerSize().getSizeName());
            EventFlower eventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(cart.getFlowerSize().getFlowerSizeID());
            if (eventFlower !=null && eventFlower.getSaleoff() != null)
            {
                BigDecimal discountAmount = cart.getFlowerSize().getPrice().multiply(eventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
                cartDTO.setProductPriceEvent(cart.getFlowerSize().getPrice().subtract(discountAmount));
                cartDTO.setSaleOff(eventFlower.getSaleoff());
            }
            cartDTO.setNumber(cart.getQuantity());
            cartDTO.setStatus(cart.getStatus());
            cartDTO.setProductID(cart.getFlowerSize().getFlower().getFlowerID());
            cartDTO.setAvatar(cart.getFlowerSize().getFlower().getImage());
            cartDTO.setProductTitle(cart.getFlowerSize().getFlower().getName());
            cartDTO.setProductPrice(cart.getFlowerSize().getPrice());
            cartDTO.setPurposeID(cart.getFlowerSize().getFlower().getPurpose().getPurposeID());
            cartDTO.setCategoryID(cart.getFlowerSize().getFlower().getCategory().getCategoryID());
            cartDTO.setType(String.valueOf(org.example.entity.enums.Type.Order));
            // Fetch sizes and stock information
            List<String> sizes = cart.getFlowerSize().getFlower().getFlowerSizes()
                    .stream()
                    .map(FlowerSize::getSizeName)
                    .collect(Collectors.toList());
            List<Integer> stockList = flowerSizeService.findFlowerSizeByProductID(cart.getFlowerSize().getFlower().getFlowerID())
                    .stream()
                    .map(FlowerSize::getStock)
                    .collect(Collectors.toList());

            cartDTO.setSizes(sizes);
            cartDTO.setStock(stockList);

            return cartDTO;
        }).collect(Collectors.toList());
        List<CartDTO> cartPreorderList = cartpreorderList.stream().map(cart -> {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCartID(cart.getCartID());
            cartDTO.setSizeChoose(cart.getFlowerSize().getSizeName());
            cartDTO.setNumber(cart.getQuantity());
            cartDTO.setStatus(cart.getStatus());
            cartDTO.setProductID(cart.getFlowerSize().getFlower().getFlowerID());
            cartDTO.setAvatar(cart.getFlowerSize().getFlower().getImage());
            cartDTO.setProductTitle(cart.getFlowerSize().getFlower().getName());
            cartDTO.setProductPrice(cart.getFlowerSize().getPrice());
            cartDTO.setPurposeID(cart.getFlowerSize().getFlower().getPurpose().getPurposeID());
            cartDTO.setCategoryID(cart.getFlowerSize().getFlower().getCategory().getCategoryID());
            cartDTO.setType(String.valueOf(org.example.entity.enums.Type.Preorder));
            // Fetch sizes and stock information
            List<String> sizes = cart.getFlowerSize().getFlower().getFlowerSizes()
                    .stream()
                    .map(FlowerSize::getSizeName)
                    .collect(Collectors.toList());
            List<Integer> stockList = flowerSizeService.findFlowerSizeByProductID(cart.getFlowerSize().getFlower().getFlowerID())
                    .stream()
                    .map(FlowerSize::getStock)
                    .collect(Collectors.toList());

            cartDTO.setSizes(sizes);
            cartDTO.setStock(stockList);

            return cartDTO;
        }).collect(Collectors.toList());


        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("account", account);
        response.put("cartorder", cartOrderList);
        response.put("cartpreorder", cartPreorderList);
        response.put("discount", discounts);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Integer id, @RequestBody CartUpdateRequest request) {

        Cart cart =cartService.getCartById(id);
        FlowerSize FlowerSize = flowerSizeService.findFlowerSizeByProductIDAndSize(cart.getFlowerSize().getFlower().getFlowerID(), request.getSize());
        if (FlowerSize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product size not found");
        }

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart.setQuantity(request.getNumber());
        cart.setFlowerSize(FlowerSize);

        Cart updatedCart = cartService.updateCart(id, cart);
        return ResponseEntity.ok(updatedCart);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer id){
        int idAccount = getIDAccountFromAuthService.common();
        cartService.hardDeleteCart(id);
        notifyCartUpdate(idAccount, cartCount(idAccount));

        return ResponseEntity.noContent().build();
    }
    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(@RequestParam("cartID") int[] cartIDs, @RequestParam("price") BigDecimal[] prices, @RequestParam(value= "paid", required = false) BigDecimal[] paids, @RequestParam(value = "discount") int discountid, @RequestBody BuyInfo buyInfo) {
        try {
            int id = getIDAccountFromAuthService.common();
            Account account = accountService.getAccountById(id);
            int firstCartID = cartIDs[0];
            Cart firstCart = cartService.findCartByCartID(firstCartID);
            Discount discount = discountService.findDiscountByID(discountid);
            if (discount.getAccount() != null && discount.getAccount().equals(account)) {
                discount.setStatus(Status.DISABLE);
                discountRepository.save(discount);
            }
            if (firstCart.getType()== org.example.entity.enums.Type.Order)
            {
                Order newBill = new Order();
                newBill.setAccountID(account);
                newBill.setPaid(IsPaid.No);
                newBill.setStatus(Status.ENABLE);
                newBill.setDate(LocalDateTime.now());
                newBill.setCondition(Condition.Pending);
                newBill.setName(buyInfo.getName());
                newBill.setNote(buyInfo.getNote());
                newBill.setDeliveryAddress(buyInfo.getAddress());
                newBill.setPhoneNumber(buyInfo.getPhone());
                BigDecimal totalAmount = new BigDecimal(0);
                newBill.setHadpaid(totalAmount);
                newBill.setTotalAmount(totalAmount);
                orderRepository.save(newBill);

                for (int i = 0; i < cartIDs.length; i++) {
                    int cartID = cartIDs[i];
                    BigDecimal price = prices[i];
                    totalAmount = totalAmount.add(price);

                    Cart cart = cartService.findCartByCartID(cartID);
                    if (cart == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Cart item with ID " + cartID + " not found.");
                    }

                    int number = cart.getQuantity();
                    FlowerSize FlowerSize = cart.getFlowerSize();

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderID(newBill);  // Gán Order đã lưu vào OrderDetail
                    orderDetail.setFlowerSize(cart.getFlowerSize());
                    orderDetail.setQuantity(cart.getQuantity());
                    orderDetail.setPrice(price);
                    orderDetail.setPaid(BigDecimal.ZERO);
                    orderDetail.setStatus(Status.ENABLE);
                    cartService.deleteBoughtCart(cartID);
                    flowerSizeService.updateStock(FlowerSize.getFlowerSizeID(), number);
                    orderDetailRepository.save(orderDetail);

                }
                newBill.setTotalAmount(totalAmount);
                orderRepository.save(newBill);
                notificationController.orderConditionNotification(newBill.getOrderID());
                emailController.BuySuccess(newBill,id);
            }
            else {
                Preorder newPreorder = new Preorder();
                newPreorder.setAccount(account);
                newPreorder.setStatus(Status.ENABLE);
                newPreorder.setDate(LocalDateTime.now());
                newPreorder.setName(buyInfo.getName());
                newPreorder.setNote(buyInfo.getNote());
                newPreorder.setDeliveryAddress(buyInfo.getAddress());
                newPreorder.setPhoneNumber(buyInfo.getPhone());
                BigDecimal totalAmount = new BigDecimal(0);
                newPreorder.setTotalAmount(totalAmount);
                newPreorder.setPrecondition(Precondition.Waiting);
                preOrderRepository.save(newPreorder);

                for (int i = 0; i < cartIDs.length; i++) {
                    int cartID = cartIDs[i];
                    BigDecimal price = prices[i];
                    BigDecimal paid = paids[i];

                    totalAmount = totalAmount.add(price);

                    Cart cart = cartService.findCartByCartID(cartID);
                    if (cart == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Cart item with ID " + cartID + " not found.");
                    }
                    Preorderdetail preorderdetail = new Preorderdetail();
                    preorderdetail.setPreorderID(newPreorder);
                    preorderdetail.setFlowerSize(cart.getFlowerSize());
                    preorderdetail.setQuantity(cart.getQuantity());
                    preorderdetail.setPrice(price);
                    preorderdetail.setStatus(Status.ENABLE);
                    preorderdetail.setPaid(paid);

                    cartService.deleteBoughtCart(cartID);
                    preorderdetailRepository.save(preorderdetail);
                }
                newPreorder.setTotalAmount(totalAmount);
                preOrderRepository.save(newPreorder);
                emailController.PreorderSuccess(newPreorder,id);
                notificationController.preOrderCreateNotification(newPreorder.getId());

            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product purchased successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product: " + e.getMessage());
        }
    }

    public ResponseEntity<?> buyVNPay(int[] cartIDs, int accountId,BigDecimal[] prices, BigDecimal[] paids, BuyInfo buyInfo, String vnp_TransactionNo, int discountid) {
        try {
            Account account = accountService.getAccountById(accountId);
            int firstCartID = cartIDs[0];
            Cart firstCart = cartService.findCartByCartID(firstCartID);
            Discount discount = discountService.findDiscountByID(discountid);
            if (discount.getAccount() != null && discount.getAccount().equals(account)) {
                discount.setStatus(Status.DISABLE);
                discountRepository.save(discount);
            }
            if (firstCart.getType()== org.example.entity.enums.Type.Order)
            {
                Order newBill = new Order();
                newBill.setAccountID(account);
                newBill.setPaid(IsPaid.Yes);
                newBill.setStatus(Status.ENABLE);
                newBill.setDate(LocalDateTime.now());
                newBill.setCondition(Condition.Pending);
                newBill.setName(buyInfo.getName());
                newBill.setNote(buyInfo.getNote());
                newBill.setVnp_TransactionNo(vnp_TransactionNo);
                newBill.setDeliveryAddress(buyInfo.getAddress());
                newBill.setPhoneNumber(buyInfo.getPhone());
                BigDecimal totalAmount = new BigDecimal(0);
                newBill.setTotalAmount(totalAmount);
                orderRepository.save(newBill);

                for (int i = 0; i < cartIDs.length; i++) {
                    int cartID = cartIDs[i];
                    BigDecimal price = prices[i];
                    totalAmount = totalAmount.add(price);

                    Cart cart = cartService.findCartByCartID(cartID);
                    if (cart == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Cart item with ID " + cartID + " not found.");
                    }

                    int number = cart.getQuantity();
                    FlowerSize FlowerSize = cart.getFlowerSize();

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderID(newBill);  // Gán Order đã lưu vào OrderDetail
                    orderDetail.setFlowerSize(cart.getFlowerSize());
                    orderDetail.setQuantity(cart.getQuantity());
                    orderDetail.setPrice(price);
                    orderDetail.setPaid(price);
                    orderDetail.setStatus(Status.ENABLE);
                    cartService.deleteBoughtCart(cartID);
                    flowerSizeService.updateStock(FlowerSize.getFlowerSizeID(), number);
                    orderDetailRepository.save(orderDetail);


                }

                newBill.setTotalAmount(totalAmount);
                newBill.setHadpaid(totalAmount);
                orderRepository.save(newBill);
                BigDecimal consume = account.getConsume().add(totalAmount);
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
                emailController.BuySuccess(newBill,accountId);
                notificationController.orderConditionNotification(newBill.getOrderID());
            }
            else {
                Preorder newPreorder = new Preorder();
                newPreorder.setAccount(account);
                newPreorder.setStatus(Status.ENABLE);
                newPreorder.setDate(LocalDateTime.now());
                newPreorder.setName(buyInfo.getName());
                newPreorder.setNote(buyInfo.getNote());
                newPreorder.setDeliveryAddress(buyInfo.getAddress());
                newPreorder.setPhoneNumber(buyInfo.getPhone());
                newPreorder.setVnp_TransactionNo(vnp_TransactionNo);
                newPreorder.setPrecondition(Precondition.Waiting);
                BigDecimal totalAmount = new BigDecimal(0);
                BigDecimal hadPaid = new BigDecimal(0);
                newPreorder.setTotalAmount(totalAmount);
                preOrderRepository.save(newPreorder);

                for (int i = 0; i < cartIDs.length; i++) {
                    int cartID = cartIDs[i];
                    BigDecimal price = prices[i];
                    BigDecimal paid = paids[i];

                    totalAmount = totalAmount.add(price);
                    hadPaid = hadPaid.add(paid);
                    Cart cart = cartService.findCartByCartID(cartID);
                    if (cart == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Cart item with ID " + cartID + " not found.");
                    }
                    Preorderdetail preorderdetail = new Preorderdetail();
                    preorderdetail.setPreorderID(newPreorder);
                    preorderdetail.setFlowerSize(cart.getFlowerSize());
                    preorderdetail.setQuantity(cart.getQuantity());
                    preorderdetail.setPrice(price);
                    preorderdetail.setStatus(Status.ENABLE);
                    preorderdetail.setPaid(paid);
                    cartService.deleteBoughtCart(cartID);
                    preorderdetailRepository.save(preorderdetail);

                    BigDecimal consume = account.getConsume().add(hadPaid);
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
                }
                accountRepository.save(account);
                newPreorder.setTotalAmount(totalAmount);
                preOrderRepository.save(newPreorder);
                emailController.PreorderSuccess(newPreorder,accountId);
                notificationController.preOrderCreateNotification(newPreorder.getId());

            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product purchased successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product: " + e.getMessage());
        }
    }

    @RequestMapping("/checkDiscount")
    private ResponseEntity<?> checkDiscount(@RequestBody String discountCode) {
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);

        Discount discount = discountService.findDiscountByName(discountCode);

        if (discount == null) {
            return ResponseEntity.badRequest().body("Mã giảm giá không tồn tại");
        }

        if (discount.getAccount() != null && !discount.getAccount().equals(account)) {
            return ResponseEntity.badRequest().body("Bạn không thể dùng mã này");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("detailDiscount", discount);
        return ResponseEntity.ok(response);
    }

}