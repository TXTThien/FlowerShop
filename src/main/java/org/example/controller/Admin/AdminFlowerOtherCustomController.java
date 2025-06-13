package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.FloOthCustomDTO;
import org.example.entity.FlowerCustom;
import org.example.entity.OtherCustom;
import org.example.entity.enums.Status;
import org.example.repository.FlowerCustomRepository;
import org.example.repository.OtherCustomRepository;
import org.example.service.IFlowerCustomService;
import org.example.service.IOtherCustomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/floother")
@RequiredArgsConstructor
public class AdminFlowerOtherCustomController {
    private final IFlowerCustomService flowerCustomService;
    private final IOtherCustomService otherCustomService;
    private final FlowerCustomRepository flowerCustomRepository;
    private final OtherCustomRepository otherCustomRepository;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<FlowerCustom> flowerCustoms = flowerCustomRepository.findAll();
        List<OtherCustom> otherCustoms = otherCustomRepository.findAll();
        List<FloOthCustomDTO> floOthCustomDTOS = new ArrayList<>();
        for (FlowerCustom flowerCustom : flowerCustoms) {
            FloOthCustomDTO dto = new FloOthCustomDTO();
            dto.setFlowerID(flowerCustom.getFlowerID());
            dto.setFlowerName(flowerCustom.getName());
            dto.setPrice(flowerCustom.getPrice());
            dto.setStatus(String.valueOf(flowerCustom.getStatus()));
            dto.setType("flower");
            floOthCustomDTOS.add(dto);
        }

        for (OtherCustom otherCustom : otherCustoms) {
            FloOthCustomDTO dto = new FloOthCustomDTO();
            dto.setOtherID(otherCustom.getOtherID());
            dto.setOtherName(otherCustom.getName());
            dto.setPrice(otherCustom.getPrice());
            dto.setStatus(String.valueOf(otherCustom.getStatus()));
            dto.setType("other");
            floOthCustomDTOS.add(dto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("floOthCustomDTOS", floOthCustomDTOS);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody FloOthCustomDTO floOthCustomDTO){
        if (Objects.equals(floOthCustomDTO.getType(), "flower")) {
            FlowerCustom flowerCustom = new FlowerCustom();
            flowerCustom.setStatus(Status.valueOf(floOthCustomDTO.getStatus()));
            flowerCustom.setPrice(floOthCustomDTO.getPrice());
            flowerCustom.setName(floOthCustomDTO.getFlowerName());

            flowerCustomRepository.save(flowerCustom);
            return ResponseEntity.ok("Thêm flower thành công");
        } else if (Objects.equals(floOthCustomDTO.getType(), "other")) {
            OtherCustom otherCustom = new OtherCustom();
            otherCustom.setStatus(Status.valueOf(floOthCustomDTO.getStatus()));
            otherCustom.setPrice(floOthCustomDTO.getPrice());
            otherCustom.setName(floOthCustomDTO.getOtherName());

            otherCustomRepository.save(otherCustom);
            return ResponseEntity.ok("Thêm other thành công");
        } else {
            return ResponseEntity.badRequest().body("Type không hợp lệ (phải là 'flower' hoặc 'other')");
        }
    }

    @PutMapping()
    public ResponseEntity<?> put(@RequestBody FloOthCustomDTO floOthCustomDTO){
        if (Objects.equals(floOthCustomDTO.getType(), "flower")) {
            FlowerCustom flowerCustom = flowerCustomService.findFlowerByID(floOthCustomDTO.getFlowerID());
            flowerCustom.setStatus(Status.valueOf(floOthCustomDTO.getStatus()));
            flowerCustom.setPrice(floOthCustomDTO.getPrice());
            flowerCustom.setName(floOthCustomDTO.getFlowerName());
            flowerCustomRepository.save(flowerCustom);
            return ResponseEntity.ok("Update flower thành công");
        } else if (Objects.equals(floOthCustomDTO.getType(), "other")) {
            OtherCustom otherCustom = otherCustomService.findOtherByID(floOthCustomDTO.getOtherID());
            otherCustom.setStatus(Status.valueOf(floOthCustomDTO.getStatus()));
            otherCustom.setPrice(floOthCustomDTO.getPrice());
            otherCustom.setName(floOthCustomDTO.getOtherName());

            otherCustomRepository.save(otherCustom);
            return ResponseEntity.ok("Update other thành công");
        } else {
            return ResponseEntity.badRequest().body("Type không hợp lệ (phải là 'flower' hoặc 'other')");
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody FloOthCustomDTO floOthCustomDTO){
        if (Objects.equals(floOthCustomDTO.getType(), "flower")) {
            FlowerCustom flowerCustom = flowerCustomService.findFlowerByID(floOthCustomDTO.getFlowerID());
            if (flowerCustom.getStatus() == Status.ENABLE)
                flowerCustom.setStatus(Status.DISABLE);
            else
                flowerCustom.setStatus(Status.ENABLE);
            flowerCustomRepository.save(flowerCustom);
            return ResponseEntity.ok("Update flower thành công");
        } else if (Objects.equals(floOthCustomDTO.getType(), "other")) {
            OtherCustom otherCustom = otherCustomService.findOtherByID(floOthCustomDTO.getOtherID());
            if (otherCustom.getStatus() == Status.ENABLE)
                otherCustom.setStatus(Status.DISABLE);
            else
                otherCustom.setStatus(Status.ENABLE);
            otherCustomRepository.save(otherCustom);
            return ResponseEntity.ok("Thêm other thành công");
        } else {
            return ResponseEntity.badRequest().body("Type không hợp lệ (phải là 'flower' hoặc 'other')");
        }
    }
}
