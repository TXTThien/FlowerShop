package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.ChatRequest;
import org.example.dto.ChatResponse;
import org.example.dto.FlowerDTO;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.regex.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${openrouter.api.key}")
    private String openRouterKey;

    private final String OPENROUTER_URL = "https://api.openai.com/v1/chat/completions";
    private final IFlowerService flowerService;
    private final IDetectService detectService;
    private final IDetectFlowerService detectFlowerService;
    private final IFlowerSizeService flowerSizeService;
    private final IEventFlowerService eventFlowerService;
    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequest request) {
        try {
            if (request == null || request.getMessages() == null) {
                return ResponseEntity.badRequest().body("Request không hợp lệ");
            }

            RestTemplate restTemplate = new RestTemplate();

            // Lấy danh sách messages ban đầu và tạo bản sao
            List<Map<String, String>> originalMessages = new ArrayList<>(request.getMessages());

            // Thêm yêu cầu trả lời ngắn gọn
            Map<String, String> conciseInstruction = new HashMap<>();
            conciseInstruction.put("role", "user");
            conciseInstruction.put("text", "trả lời ngắn gọn, không lan man, cách nhau dấu phẩy");
            originalMessages.add(conciseInstruction);

            // Giới hạn số lượng message (chỉ lấy 5 cuối cùng)
            int limit = 5;
            int startIndex = Math.max(0, originalMessages.size() - limit);
            List<Map<String, String>> limitedMessages = originalMessages.subList(startIndex, originalMessages.size());

            // Tạo request body đúng theo định dạng API yêu cầu
            Map<String, Object> requestBody = new HashMap<>();
            List<Map<String, Object>> contents = new ArrayList<>();

            // Tạo phần nội dung theo cấu trúc API yêu cầu
            for (Map<String, String> message : limitedMessages) {
                Map<String, Object> content = new HashMap<>();

                // Lấy role từ message, mặc định là "user"
                String role = message.getOrDefault("role", "user");
                content.put("role", role);

                // Tạo phần "parts"
                List<Map<String, Object>> parts = new ArrayList<>();
                Map<String, Object> part = new HashMap<>();

                String text = Optional.ofNullable(message.get("text"))
                        .filter(t -> !t.trim().isEmpty())
                        .orElse(message.get("content"));
                if (text != null && !text.trim().isEmpty()) {
                    part.put("text", text);
                    parts.add(part);
                    content.put("parts", parts);
                    contents.add(content);
                } else {
                    System.err.println("⚠️ Bỏ qua message do thiếu nội dung: " + message);
                }
            }

            requestBody.put("contents", contents);

            // Cập nhật URL để truyền API Key qua query string
            String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + openRouterKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

            if (response == null || response.getBody() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không nhận được phản hồi từ OpenAI");
            }

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không có phản hồi từ Gemini");
            }

            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> contentMap = (Map<String, Object>) firstCandidate.get("content");
            List<Map<String, String>> parts = (List<Map<String, String>>) contentMap.get("parts");

            String content = "";
            if (parts != null && !parts.isEmpty()) {
                content = parts.get(0).get("text"); // lấy nội dung trả lời đầu tiên
            }


            if (content == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không có nội dung trả về từ OpenAI");
            }
// Bước 1: Trích cụm từ giữa **...**
            Pattern pattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
            Matcher matcher = pattern.matcher(content);
            List<String> flowers = new ArrayList<>();
            while (matcher.find()) {
                String flower = matcher.group(1).trim().toLowerCase();
                flowers.add(flower);
            }

// Bước 2: Nếu không có, dùng regex bắt cụm "hoa + tên"
            if (flowers.isEmpty()) {
                Pattern namePattern = Pattern.compile("\\bhoa\\s+[\\p{L}\\p{M}\\s\\(\\)]+(?=[,\\.\\)]|\\shoặc|$)", Pattern.CASE_INSENSITIVE);
                Matcher nameMatcher = namePattern.matcher(content);

                while (nameMatcher.find()) {
                    String flower = nameMatcher.group().trim().toLowerCase();

                    // Loại bỏ phần trong ngoặc đơn
                    flower = flower.replaceAll("\\s*\\([^\\)]*\\)", "").trim();

                    // Tránh thêm trùng lặp
                    if (!flower.isEmpty() && !flowers.contains(flower)) {
                        flowers.add(flower);
                    }
                }
            }


// In ra các loại hoa tìm được
            System.out.println("Flowers: " + flowers);

// Gọi findFlower
            List<FlowerDTO> flowerDTOList = findFlower(flowers);
            if (flowerDTOList != null && !flowerDTOList.isEmpty()) {
                content += "\nDưới đây là các sản phẩm được đề xuất";
            }
            else {
                // Bước fallback: tìm trong tin nhắn cuối của người dùng
                String lastUserMessage = "";
                for (int i = request.getMessages().size() - 1; i >= 0; i--) {
                    Map<String, String> msg = request.getMessages().get(i);
                    if ("user".equalsIgnoreCase(msg.get("role"))) {
                        lastUserMessage = Optional.ofNullable(msg.get("text"))
                                .orElse(msg.get("content"))
                                .toLowerCase();
                        break;
                    }
                }

                // Phân tích lại theo cụm "mua|muốn|cần hoa ..."
                Pattern flowerPattern = Pattern.compile("(?:mua|muốn|cần|đặt|ý nghĩa|Ý nghĩa|của|Tác dụng|tác dụng|Ngôn ngữ|ngôn ngữ loài hoa|Công dụng|công dụng|Tìm)\\s*hoa\\s*([\\p{L}\\s]+)", Pattern.CASE_INSENSITIVE);
                Matcher flowerMatcher = flowerPattern.matcher(lastUserMessage);

                flowers.clear();

                while (flowerMatcher.find()) {
                    String flower = flowerMatcher.group(1).trim().toLowerCase();
                    if (!flower.isEmpty() && !flowers.contains(flower)) {
                        flowers.add(flower);
                    }
                }

                flowerDTOList = findFlower(flowers);

                if (flowerDTOList != null && !flowerDTOList.isEmpty()) {
                    content += "\nDưới đây là các sản phẩm được đề xuất";
                }
                else{
                    flowerPattern = Pattern.compile("(?:mua|muốn|cần|đặt|ý nghĩa|Ý nghĩa|của|Tác dụng|tác dụng|Ngôn ngữ|ngôn ngữ loài hoa|Công dụng|công dụng|Tìm)\\s*([\\p{L}\\s]+)", Pattern.CASE_INSENSITIVE);
                    flowerMatcher = flowerPattern.matcher(lastUserMessage);

                    flowers.clear();

                    while (flowerMatcher.find()) {
                        String flower = flowerMatcher.group(1).trim().toLowerCase();
                        if (!flower.isEmpty() && !flowers.contains(flower)) {
                            flowers.add(flower);
                        }
                    }
                    flowerDTOList = findFlower(flowers);
                    if (flowerDTOList != null && !flowerDTOList.isEmpty()) {
                        content += "\nDưới đây là các sản phẩm được đề xuất";
                    }
                    else {
                        flowerPattern = Pattern.compile("hoa\\s*([\\p{L}\\s]+)", Pattern.CASE_INSENSITIVE);
                        flowerMatcher = flowerPattern.matcher(lastUserMessage);

                        flowers.clear();

                        while (flowerMatcher.find()) {
                            String flower = flowerMatcher.group(1).trim().toLowerCase();
                            if (!flower.isEmpty() && !flowers.contains(flower)) {
                                flowers.add(flower);
                            }
                        }
                        flowerDTOList = findFlower(flowers);
                        if (flowerDTOList != null && !flowerDTOList.isEmpty()) {
                            content += "\nDưới đây là các sản phẩm được đề xuất";
                        }
                    }
                }

            }

            ChatResponse chatResponse = new ChatResponse(content, flowerDTOList);
            return ResponseEntity.ok(chatResponse);


        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi trong quá trình xử lý yêu cầu chat:");
            e.printStackTrace();
            System.err.println("Loại lỗi: " + e.getClass().getName());
            System.err.println("Thông điệp lỗi: " + e.getMessage());

            Throwable cause = e.getCause();
            while (cause != null) {
                System.err.println("Nguyên nhân gốc: " + cause.getClass().getName() + " - " + cause.getMessage());
                cause = cause.getCause();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }



    public List<FlowerDTO> findFlower(List<String> flowers) {
        if (flowers == null || flowers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Detect> detectList = new ArrayList<>();
        for (String flower : flowers) {
            if (flower == null || flower.trim().isEmpty()) continue;

            System.out.println("flower: " + flower);
            Detect detect = detectService.findDetectByName(flower);
            if (detect == null) {
                System.out.println("Không tìm thấy detect cho hoa: " + flower);
                continue;
            }
            detectList.add(detect);
        }

        if (detectList.isEmpty()) {
            return new ArrayList<>();
        }

        List<DetectFlower> detectFlowerList = findDetectFlowerByDetect(detectList);
        if (detectFlowerList == null || detectFlowerList.isEmpty()) {
            return new ArrayList<>();
        }

        return fromDetectToFlower(detectFlowerList);
    }

    public List<DetectFlower> findDetectFlowerByDetect(List<Detect> detectList) {
        List<DetectFlower> detectFlowerList = new ArrayList<>();
        if (detectList == null || detectList.isEmpty()) {
            return detectFlowerList;
        }

        for (int i = 0; i < detectList.size(); i++) {
            Detect currentDetect = detectList.get(i);
            if (currentDetect == null) continue;

            List<DetectFlower> detectFlowers = new ArrayList<>();

            if (detectList.size() == 1) {
                detectFlowers = detectFlowerService.findDetectFlowerByDetectAndNumber(currentDetect, 4);
            } else if (detectList.size() == 2) {
                if (detectFlowerList.isEmpty() && i == detectList.size() - 1) {
                    detectFlowers = detectFlowerService.findDetectFlowerByDetectAndNumber(currentDetect, 4);
                } else {
                    detectFlowers = detectFlowerService.findDetectFlowerByDetectAndNumber(currentDetect, 2);
                }
            } else {
                detectFlowers = detectFlowerService.findDetectFlowerByDetectAndNumber(currentDetect, 1);

                if (detectFlowers != null && detectFlowers.size() > 4) {
                    detectFlowers = detectFlowers.stream().limit(4).collect(Collectors.toList());
                } else if (detectFlowers != null && detectFlowers.size() <= 2) {
                    if (detectFlowers.size() == 1) {
                        detectFlowerList.addAll(detectFlowerService.findDetectFlowerByDetectAndNumber(detectFlowers.get(0).getDetect(), 4));
                        continue;
                    } else if (detectFlowers.size() == 2) {
                        DetectFlower df1 = detectFlowers.get(0);
                        DetectFlower df2 = detectFlowers.get(1);
                        if (df1 != null && df2 != null && df1.getDetect() != null && df1.getDetect().equals(df2.getDetect())) {
                            detectFlowerList.addAll(detectFlowerService.findDetectFlowerByDetectAndNumber(df1.getDetect(), 4));
                            continue;
                        } else {
                            detectFlowerList.addAll(detectFlowerService.findDetectFlowerByDetectAndNumber(df1.getDetect(), 2));
                            detectFlowerList.addAll(detectFlowerService.findDetectFlowerByDetectAndNumber(df2.getDetect(), 2));
                            continue;
                        }
                    }
                }
            }

            if (detectFlowers != null && !detectFlowers.isEmpty()) {
                detectFlowerList.addAll(detectFlowers);
            }
        }

        return detectFlowerList;
    }

    public List<FlowerDTO> fromDetectToFlower(List<DetectFlower> detectFlowers) {
        List<FlowerDTO> flowerDTOList = new ArrayList<>();
        if (detectFlowers == null || detectFlowers.isEmpty()) {
            return flowerDTOList;
        }

        for (DetectFlower detectFlower : detectFlowers) {
            if (detectFlower == null || detectFlower.getFlower() == null) continue;

            FlowerDTO flowerDTO = new FlowerDTO();
            flowerDTO.setFlowerID(detectFlower.getFlower().getFlowerID());
            flowerDTO.setImage(detectFlower.getFlower().getImage());
            flowerDTO.setName(detectFlower.getFlower().getName());

            FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(detectFlower.getFlower().getFlowerID());
            if (minFlowerSize == null) continue;

            List<EventFlower> eventFlower = eventFlowerService.findEventFlowersByFlowerID(minFlowerSize.getFlower().getFlowerID());
            BigDecimal minPrice = BigDecimal.ZERO;
            BigDecimal saleOff = BigDecimal.ZERO;
            BigDecimal price = BigDecimal.ZERO;

            if (eventFlower != null && !eventFlower.isEmpty()) {
                for (EventFlower eventFlower1 : eventFlower) {
                    if (eventFlower1 == null || eventFlower1.getFlowerSize() == null) continue;
                    BigDecimal eventPrice = eventFlower1.getFlowerSize().getPrice().subtract(
                            eventFlower1.getFlowerSize().getPrice().multiply(eventFlower1.getSaleoff().divide(BigDecimal.valueOf(100)))
                    );

                    if (minPrice.equals(BigDecimal.ZERO) || minPrice.compareTo(eventPrice) > 0) {
                        minPrice = eventPrice;
                        saleOff = eventFlower1.getSaleoff();
                        price = eventFlower1.getFlowerSize().getPrice();
                    }
                }
            }

            if (!minPrice.equals(BigDecimal.ZERO)) {
                flowerDTO.setPriceEvent(minPrice);
                flowerDTO.setSaleOff(saleOff);
                flowerDTO.setPrice(price);
            } else {
                flowerDTO.setPrice(minFlowerSize.getPrice());
            }

            flowerDTOList.add(flowerDTO);
        }

        return flowerDTOList;
    }

}
