package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.dto.PreorderList;
import org.example.entity.*;
import org.example.repository.PreOrderRepository;
import org.example.repository.PreorderdetailRepository;
import org.example.service.IPreOrderService;
import org.example.service.IPreorderdetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/staff/preorder")
@RequiredArgsConstructor
public class StaffPreorderController {
    private final IPreorderdetailService preorderdetailService;
    private final PreorderdetailRepository preorderdetailRepository;
    private final PreOrderRepository preOrderRepository;
    private final IPreOrderService preOrderService;
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

}
