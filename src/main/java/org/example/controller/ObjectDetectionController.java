package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.DetectDTO;
import org.example.dto.FlowerDTO;
import org.example.dto.ProductDTO;
import org.example.entity.*;
import org.example.service.IDetectFlowerService;
import org.example.service.IDetectService;
import org.example.service.IEventFlowerService;
import org.example.service.IFlowerSizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/detect")
public class ObjectDetectionController {

    private final IDetectService detectService;
    private final IDetectFlowerService detectFlowerService;
    private final IFlowerSizeService flowerSizeService;
    private final IEventFlowerService eventFlowerService;
    @PostMapping("/upload")
    public ResponseEntity<?> detectObject(@RequestParam("file") MultipartFile file) throws IOException {
        // Lưu file ảnh tạm
        File tempFile = File.createTempFile("upload_", file.getOriginalFilename());
        file.transferTo(tempFile);

        // Gọi script Python
        ProcessBuilder pb = new ProcessBuilder("python", "detect.py", tempFile.getAbsolutePath());
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Đọc output từ Python script
        String jsonResult = null;
        String imagePath = null;
        String base64Image = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("{") && line.trim().endsWith("}")) {
                jsonResult = line.trim();
            } else if (line.startsWith("IMAGE_PATH:")) {
                imagePath = line.replace("IMAGE_PATH:", "").trim();
            }
        }

        if (imagePath != null) {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        }
        List<DetectDTO> detectDTOS = new ArrayList<>();
        if (jsonResult != null) {
            ObjectMapper objectMapper = new ObjectMapper(); // com.fasterxml.jackson.databind.ObjectMapper
            Map<String, Integer> resultMap = objectMapper.readValue(jsonResult, new TypeReference<Map<String, Integer>>() {});

            for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
                DetectDTO detectDTO = getInfoDetect(entry.getKey(), entry.getValue());
                if(detectDTO == null)
                    continue;
                detectDTOS.add(detectDTO);
            }
        }

        Files.delete(tempFile.toPath());
        Map<String, Object> response = new HashMap<>();
        response.put("objects", detectDTOS);
        response.put("image", base64Image);

        return ResponseEntity.ok(response);
    }

    public DetectDTO getInfoDetect(String name,int number)
    {
        DetectDTO detectDTO = new DetectDTO();
        Detect detect = detectService.findDetectInfo(name);
        if (detect == null)
            return null;

        detectDTO.setDetect(detect);

        List<DetectFlower> detectFlowers= detectFlowerService.findDetectFlowerByDetect(detect);
        List<FlowerDTO> flowerDTOList = new ArrayList<>();
        for (DetectFlower detectFlower : detectFlowers) {
            FlowerDTO flowerDTO = new FlowerDTO();
            flowerDTO.setFlowerID(detectFlower.getFlower().getFlowerID());
            flowerDTO.setName(detectFlower.getFlower().getName());
            flowerDTO.setDescription(detectFlower.getFlower().getDescription());
            flowerDTO.setImage(detectFlower.getFlower().getImage());
            flowerDTO.setLanguageOfFlowers(detectFlower.getFlower().getLanguageOfFlowers());
            flowerDTO.setCategory(detectFlower.getFlower().getCategory());
            flowerDTO.setPurpose(detectFlower.getFlower().getPurpose());
            FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(detectFlower.getFlower().getFlowerID());
            List<EventFlower> eventFlower = eventFlowerService.findEventFlowersByFlowerID(minFlowerSize.getFlower().getFlowerID());
            BigDecimal minPrice = BigDecimal.ZERO;
            BigDecimal saleOff = BigDecimal.ZERO;
            if (!eventFlower.isEmpty())
            {
                for (EventFlower eventFlower1 : eventFlower)
                {
                    BigDecimal eventPrice = eventFlower1.getFlowerSize().getPrice().subtract(eventFlower1.getFlowerSize().getPrice().multiply(eventFlower1.getSaleoff().divide(BigDecimal.valueOf(100))));
                    if (minPrice.equals(BigDecimal.ZERO) || minPrice.compareTo(eventPrice) > 0)
                    {
                        minPrice = eventPrice;
                        saleOff = eventFlower1.getSaleoff();
                    }
                }
            }


            if (!minPrice.equals(BigDecimal.ZERO))
            {
                flowerDTO.setPriceEvent(minPrice);
                flowerDTO.setSaleOff(saleOff);
            }
            else
            {
                flowerDTO.setPriceEvent(minFlowerSize.getPrice());
            }
            flowerDTO.setPrice(minFlowerSize.getPrice());

            flowerDTOList.add(flowerDTO);
        }
        detectDTO.setFlowerDTOList(flowerDTOList);
        detectDTO.setNumberFound(number);
        return detectDTO;
    }
}
