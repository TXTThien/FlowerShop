package org.example.controller.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.config.ConfigVnpay;
import org.example.dto.BuyInfo;
import org.example.dto.OrderDeliveryDTO;
import org.example.service.ICartService;
import org.example.service.securityService.AuthService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;



@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final AuthService authService;
    private final ICartService cartService;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final UserPrebuyController prebuyController;
    private final OrderDeliveryController orderDeliveryController;
    int [] cartID;
    int [] quantity;
    BigDecimal[] price;
    BigDecimal[] paid;
    BuyInfo buyInfo;
    OrderDeliveryDTO orderDeliveryDTO;
    int discount;
    @PostMapping ("/setCart")
    public ResponseEntity<String> setCart(@RequestParam("cartID") int[] cartIDs, @RequestParam("quantities") int [] quantities , @RequestParam("price") BigDecimal[] prices,@RequestParam(value= "paid", required = false) BigDecimal[] paids, @RequestParam(value = "discount") int discountid,@RequestBody BuyInfo buyInfos){
        cartID = cartIDs;
        quantity = quantities;
        price = prices;
        buyInfo = buyInfos;
        paid = paids;
        discount = discountid;
        return ResponseEntity.ok("Cart updated successfully.");
    }
    @PostMapping ("/setOrderDelivery")
    public ResponseEntity<?> setCart(@RequestParam("price") BigDecimal[] prices, @RequestBody OrderDeliveryDTO dto ){

        price = prices;
        orderDeliveryDTO = dto;
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/pay")
    public ResponseEntity<String>  getPay(@RequestParam("totalPayment") String totalPayment) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Long.parseLong(totalPayment)*100;
        String bankCode = "NCB";
        System.out.println(buyInfo);

        String vnp_TxnRef = ConfigVnpay.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = ConfigVnpay.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", ConfigVnpay.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVnpay.hmacSHA512(ConfigVnpay.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVnpay.vnp_PayUrl + "?" + queryUrl;
        System.out.println("paymentUrl"+paymentUrl);
        return ResponseEntity.ok(paymentUrl);
    }
    @GetMapping("/payment_info")
    public void transaction(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Price: "+ Arrays.toString(price));
        String transactionId = params.get("vnp_TransactionNo");
        String responseCode = params.get("vnp_ResponseCode");
        int accountId = (int) request.getSession().getAttribute("accountID");
        if ("00".equals(responseCode)) {
            if(buyInfo!= null)
            {
                System.out.println("1");
                prebuyController.buyVNPay(cartID, accountId,price,paid,buyInfo,transactionId,discount);
            }
            else
            {
                System.out.println("2");
                orderDeliveryController.createOrderDelivery(price,orderDeliveryDTO,accountId,transactionId);
            }
            response.sendRedirect("http://localhost:8000/PaymentSuccess");

        } else {
            response.sendRedirect("http://localhost:8000/PaymentFailure");
        }
    }

}