package org.example.controller.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.dto.BuyInfo;
import org.example.dto.CartDTO;
import org.example.dto.CartUpdateRequest;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Status;
import org.example.repository.*;
import org.example.repository.OrderRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final GetIDAccountFromAuthService getIDAccountFromAuthService;

    @GetMapping("")
    public ResponseEntity<?> getCart(HttpServletRequest request) {
        int id = getIDAccountFromAuthService.common();
        Account account = accountRepository.findAccountByAccountID(id);
        List<Cart> cartList = cartService.findCartsByAccountID(id);
        List<Discount> discounts = discountRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        request.getSession().setAttribute("accountID", id);

        if (cartList != null) {
            List<CartDTO> cartDTOList = cartList.stream().map(cart -> {
                CartDTO cartDTO = new CartDTO();
                cartDTO.setCartID(cart.getCartID());
                cartDTO.setSizeChoose(cart.getFlowerSize().getSizeName());
                cartDTO.setNumber(cart.getQuantity());
                cartDTO.setStatus(cart.getStatus());
                cartDTO.setProductID(cart.getFlowerSize().getFlower().getFlowerID());
                cartDTO.setAvatar(cart.getFlowerSize().getFlower().getImage());
                cartDTO.setProductTitle(cart.getFlowerSize().getFlower().getName());
                BigDecimal priceWithBonus = cart.getFlowerSize().getPrice();
                cartDTO.setProductPrice(priceWithBonus);
                cartDTO.setPurposeID(cart.getFlowerSize().getFlower().getPurpose().getPurposeID());
                cartDTO.setCategoryID(cart.getFlowerSize().getFlower().getCategory().getCategoryID()); // Truy cập CategoryID


                List<String> sizes = cart.getFlowerSize().getFlower().getFlowerSizes()
                        .stream()
                        .map(FlowerSize::getSizeName)
                        .collect(Collectors.toList());
                List<FlowerSize> productSizes = flowerSizeService.findProductSizeByProductID(cart.getFlowerSize().getFlower().getFlowerID());
                List<Integer> stockList = new ArrayList<>();
                for (int i = 0; i < productSizes.size(); i++) {
                    FlowerSize productSize = productSizes.get(i);
                    stockList.add(productSize.getStock());
                }
                cartDTO.setSizes(sizes);
                cartDTO.setStock(stockList);

                return cartDTO;
            }).collect(Collectors.toList());
            response.put("account",account);
            response.put("cart",cartDTOList);
            response.put("discount", discounts);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Integer id, @RequestBody CartUpdateRequest request) {

        Cart cart =cartService.getCartById(id);
        FlowerSize productSize = flowerSizeService.findProductSizeByProductIDAndSize(cart.getFlowerSize().getFlower().getFlowerID(), request.getSize());
        if (productSize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product size not found");
        }

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart.setQuantity(request.getNumber());
        cart.setFlowerSize(productSize);

        Cart updatedCart = cartService.updateCart(id, cart);
        return ResponseEntity.ok(updatedCart);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer id){
        cartService.hardDeleteCart(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(@RequestParam("cartID") int[] cartIDs, @RequestParam("price") BigDecimal[] prices, @RequestBody BuyInfo buyInfo) {
        try {
            int id = getIDAccountFromAuthService.common();
            Account account = accountService.getAccountById(id);

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
            newBill.setTotalAmount(totalAmount);
            orderRepository.save(newBill);

            for (int i = 0; i < cartIDs.length; i++) {
                int cartID = cartIDs[i];
                BigDecimal price = prices[i];
                totalAmount = totalAmount.add(price);  // Tính tổng số tiền trong vòng lặp

                Cart cart = cartService.findCartByCartID(cartID);
                if (cart == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cart item with ID " + cartID + " not found.");
                }

                int number = cart.getQuantity();
                FlowerSize productSize = cart.getFlowerSize();
                prebuyService.createBillInfo(newBill, cartID,price);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderID(newBill);  // Gán Order đã lưu vào OrderDetail
                orderDetail.setFlowerSize(cart.getFlowerSize());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setPrice(price);
                orderDetail.setStatus(Status.ENABLE);
                cartService.deleteBoughtCart(cartID);
                flowerSizeService.updateStock(productSize.getFlowerSizeID(), number);
                orderDetailRepository.save(orderDetail);

            }
            newBill.setTotalAmount(totalAmount);
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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product purchased successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product: " + e.getMessage());
        }
    }

    public ResponseEntity<?> buyVNPay(int[] cartIDs, int accountId,BigDecimal[] prices, BuyInfo buyInfo) {
        try {
            Account account = accountService.getAccountById(accountId);
            Order newBill = new Order();
            newBill.setAccountID(account);
            newBill.setPaid(IsPaid.Yes);
            newBill.setStatus(Status.ENABLE);
            newBill.setDate(LocalDateTime.now());
            newBill.setCondition(Condition.Pending);
            newBill.setName(buyInfo.getName());
            newBill.setNote(buyInfo.getNote());
            newBill.setDeliveryAddress(buyInfo.getAddress());
            newBill.setPhoneNumber(buyInfo.getPhone());
            BigDecimal totalAmount = new BigDecimal(0);
            newBill.setTotalAmount(totalAmount);
            orderRepository.save(newBill);

            for (int i = 0; i < cartIDs.length; i++) {
                int cartID = cartIDs[i];
                BigDecimal price = prices[i];
                totalAmount = totalAmount.add(price);  // Tính tổng số tiền trong vòng lặp

                Cart cart = cartService.findCartByCartID(cartID);
                if (cart == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cart item with ID " + cartID + " not found.");
                }

                int number = cart.getQuantity();
                FlowerSize productSize = cart.getFlowerSize();
                prebuyService.createBillInfo(newBill, cartID,price);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderID(newBill);  // Gán Order đã lưu vào OrderDetail
                orderDetail.setFlowerSize(cart.getFlowerSize());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setPrice(price);
                orderDetail.setStatus(Status.ENABLE);
                cartService.deleteBoughtCart(cartID);
                flowerSizeService.updateStock(productSize.getFlowerSizeID(), number);
                orderDetailRepository.save(orderDetail);

            }
            newBill.setTotalAmount(totalAmount);
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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product purchased successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product: " + e.getMessage());
        }
    }
}