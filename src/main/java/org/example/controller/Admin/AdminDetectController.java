package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.DetectDTO;
import org.example.dto.DetectInfo;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.DetectFlowerRepository;
import org.example.repository.DetectRepository;
import org.example.service.IDetectFlowerService;
import org.example.service.IDetectService;
import org.example.service.IFlowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/detect")
@RequiredArgsConstructor
public class AdminDetectController {
    private final IDetectService detectService;
    private final DetectRepository detectRepository;
    private final DetectFlowerRepository detectFlowerRepository;
    private final IDetectFlowerService detectFlowerService;
    private final IFlowerService flowerService;
    @GetMapping("")
    public ResponseEntity<?> getAllDetect() {
        List<Detect> detects = detectRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("detects", detects);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailDetect(@PathVariable int id) {
        Detect detect = detectService.findDetectByDetectID(id);
        List<DetectFlower> detectFlowers = detectFlowerService.findDetectFlowerByDetect(detect);
        Map<String, Object> response = new HashMap<>();
        response.put("detect", detect);
        response.put("detectFlowers", detectFlowers);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> postDetailDetect(@RequestBody DetectInfo detectInfo) {
        Detect detect = detectInfo.getDetect();
        if (detect.getFlowerdetect() == null || detect.getFlowerdetect().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No flowers detected for this entry.");
        }
        detectRepository.save(detect);
        List<DetectFlower> detectFlowers = new ArrayList<>();
        for (int i = 0; i<detectInfo.getFlowerId().size();i++)
        {
            DetectFlower detectFlower = new DetectFlower();
            detectFlower.setDetect(detect);
            detectFlower.setFlower(flowerService.getProductById(detectInfo.getFlowerId().get(i)));
            detectFlowers.add(detectFlower);
        }
        detectFlowerRepository.saveAll(detectFlowers);
        return ResponseEntity.ok("Detect created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putDetailDetect(@PathVariable int id, @RequestBody DetectInfo detectInfo) {
        Detect detect = detectService.findDetectByDetectID(id);
        Detect update = detectInfo.getDetect();
        if (update.getFlowerdetect() == null || update.getFlowerdetect().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No flowers detected for this entry.");
        }
        detect.setFlowerdetect(update.getFlowerdetect());
        detect.setVietnamname(update.getVietnamname());
        detect.setImageurl(update.getImageurl());
        detect.setOrigin(update.getOrigin());
        detect.setTimebloom(update.getTimebloom());
        detect.setCharacteristic(update.getCharacteristic());
        detect.setFlowerlanguage(update.getFlowerlanguage());
        detect.setUses(update.getUses());
        detect.setBonus(update.getBonus());
        detect.setStatus(update.getStatus());
        detectRepository.save(detect);
        List<DetectFlower> detectFlowerList = detectFlowerService.findDetectFlowerByDetect(detect);
        detectFlowerRepository.deleteAll(detectFlowerList);
        List<DetectFlower> detectFlowers = new ArrayList<>();
        for (int i = 0; i<detectInfo.getFlowerId().size();i++)
        {
            DetectFlower detectFlower = new DetectFlower();
            detectFlower.setDetect(detect);
            detectFlower.setFlower(flowerService.getProductById(detectInfo.getFlowerId().get(i)));
            detectFlowers.add(detectFlower);
        }
        detectFlowerRepository.saveAll(detectFlowers);
        return ResponseEntity.ok("Detect updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDetailDetect(@PathVariable int id) {
        Detect detect = detectService.findDetectByDetectID(id);
        if (detect.getStatus() == Status.ENABLE)
        {
            detect.setStatus(Status.DISABLE);
            detectRepository.save(detect);
            return ResponseEntity.ok("Xóa nhận diện thành công");
        }
        else
        {
            detect.setStatus(Status.ENABLE);
            detectRepository.save(detect);
            return ResponseEntity.ok("Hoàn tác nhận diện thành công");
        }
    }

}
