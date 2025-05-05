package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.entity.enums.TypeGift;
import org.example.repository.*;
import org.example.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staffrollbar")
@RequiredArgsConstructor
public class StaffRollBarController {
    private final IRollBarService rollBarService;
    private final IGiftService giftService;
    private final GiftRepository giftRepository;
    private final RollBarRepository rollBarRepository;
    private final IFlowerService flowerService;
    private final IFlowerSizeService flowerSizeService;
    private final CategoryRepository categoryRepository;
    private final PurposeRepository purposeRepository;
    private final TypeRepository typeRepository;
    @GetMapping("")
    private ResponseEntity<?> getRollBarInfo(){
        List<RollBar> rollBarList = rollBarRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("rollBarList", rollBarList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getDetailRollBar(@PathVariable int id){
        RollBar rollBar = rollBarService.findRollBarByIdByAdmin(id);
        List<Gift> gifts = giftService.findGiftsByRollBarByAdmin(id);
        List<Flower> flowers = flowerService.findAll();
        List<FlowerInfo> flowerInfos = new ArrayList<>();
        for (Flower flower : flowers)
        {
            FlowerInfo flowerInfo = new FlowerInfo();
            flowerInfo.setName(flower.getName());
            flowerInfo.setId(flowerInfo.getId());
            flowerInfo.setImage(flowerInfo.getImage());
            List<FlowerSize> flowerSizes = flowerSizeService.findFlowerSizeByProductID(flower.getFlowerID());
            List<FlowerSizeDTO> flowerSizeDTOS = new ArrayList<>();
            for (FlowerSize flowerSize : flowerSizes)
            {
                FlowerSizeDTO flowerSizeDTO = new FlowerSizeDTO();
                flowerSizeDTO.setSizeName(flowerSizeDTO.getSizeName());
                flowerSizeDTO.setFlowerSizeID(flowerSizeDTO.getFlowerSizeID());
                flowerSizeDTOS.add(flowerSizeDTO);
            }
            flowerInfo.setFlowerSizeDTOS(flowerSizeDTOS);
            flowerInfos.add(flowerInfo);
        }
        TypeGift[] typeGifts = TypeGift.values();
        List<Category> categories = categoryRepository.findAll();
        List<Type> types = typeRepository.findAll();
        List<Purpose> purposes =purposeRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("rollBar", rollBar);
        response.put("gifts", gifts);
        response.put("flowers", flowers);
        response.put("typeGifts", typeGifts);
        response.put("categories", flowers);
        response.put("types", flowers);
        response.put("purposes", flowers);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    private ResponseEntity<?> postRollBar (@RequestBody RollBarInfoDTOStaff rollBarInfoDTOStaff)
    {
        RollBar rollBar = new RollBar();
        rollBar.setStatus(rollBarInfoDTOStaff.getStatus());
        rollBar.setName(rollBarInfoDTOStaff.getName());
        rollBar.setColor(rollBarInfoDTOStaff.getColor());
        rollBar.setDays(rollBarInfoDTOStaff.getDays());
        rollBarRepository.save(rollBar);
        for (GiftInfoDTOStaff giftInfoDTOStaff : rollBarInfoDTOStaff.getGiftInfoDTOStaffList())
        {
            Gift gift = new Gift();
            gift.setName(giftInfoDTOStaff.getName());
            gift.setTypeGift(TypeGift.valueOf(giftInfoDTOStaff.getTypegift()));
            if (giftInfoDTOStaff.getDiscountpercent() != null)
            {
                gift.setDiscountpercent(giftInfoDTOStaff.getDiscountpercent());
                if (giftInfoDTOStaff.getPurposeid() != null)
                    gift.setPurpose(purposeRepository.findPurposeByPurposeID(giftInfoDTOStaff.getPurposeid()));
                if (giftInfoDTOStaff.getTypeid() != null)
                    gift.setType(typeRepository.findTypeByTypeID(giftInfoDTOStaff.getTypeid()));
                if (giftInfoDTOStaff.getCategoryid() != null)
                    gift.setCategoryID(categoryRepository.findCategoryByCategoryID(giftInfoDTOStaff.getCategoryid()));
                gift.setTimeEnd(giftInfoDTOStaff.getTimeend());
            }
            if (giftInfoDTOStaff.getFlowerSizeid() != null)
                gift.setFlowersizeid(flowerSizeService.findFlowerSizeByID(giftInfoDTOStaff.getFlowerSizeid()));
            gift.setDescription(giftInfoDTOStaff.getDescription());
            gift.setStatus(giftInfoDTOStaff.getStatus());
            gift.setPercent(giftInfoDTOStaff.getPercent());
            gift.setRollbarid(rollBar);
            giftRepository.save(gift);
        }
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> putRollBar (@RequestBody RollBarInfoDTOStaff rollBarInfoDTOStaff, @PathVariable int id)
    {
        RollBar rollBar = rollBarService.findRollBarByIdByAdmin(id);
        rollBar.setStatus(rollBarInfoDTOStaff.getStatus());
        rollBar.setName(rollBarInfoDTOStaff.getName());
        rollBar.setColor(rollBarInfoDTOStaff.getColor());
        rollBar.setDays(rollBarInfoDTOStaff.getDays());
        rollBarRepository.save(rollBar);
        List<Gift> gifts = giftService.findGiftsByRollBar(rollBar);
        List<Integer> updatedGiftIds = rollBarInfoDTOStaff.getGiftInfoDTOStaffList().stream()
                .map(GiftInfoDTOStaff::getGiftid)
                .filter(Objects::nonNull) // chỉ lấy id không null
                .toList();

        List<Gift> deleteGifts = new ArrayList<>();
        for (Gift gift : gifts) {
            if (!updatedGiftIds.contains(gift.getId())) {
                deleteGifts.add(gift);
            }
        }

        giftRepository.deleteAll(deleteGifts);


        for (GiftInfoDTOStaff giftInfoDTOStaff : rollBarInfoDTOStaff.getGiftInfoDTOStaffList())
        {
            Gift gift;
            gift = giftInfoDTOStaff.getGiftid() != null
                    ? giftRepository.findGiftById(giftInfoDTOStaff.getGiftid())
                    : new Gift();


            gift.setName(giftInfoDTOStaff.getName());
            gift.setTypeGift(TypeGift.valueOf(giftInfoDTOStaff.getTypegift()));
            if (giftInfoDTOStaff.getDiscountpercent() != null)
            {
                gift.setDiscountpercent(giftInfoDTOStaff.getDiscountpercent());
                if (giftInfoDTOStaff.getPurposeid() != null)
                    gift.setPurpose(purposeRepository.findPurposeByPurposeID(giftInfoDTOStaff.getPurposeid()));
                if (giftInfoDTOStaff.getTypeid() != null)
                    gift.setType(typeRepository.findTypeByTypeID(giftInfoDTOStaff.getTypeid()));
                if (giftInfoDTOStaff.getCategoryid() != null)
                    gift.setCategoryID(categoryRepository.findCategoryByCategoryID(giftInfoDTOStaff.getCategoryid()));
                gift.setTimeEnd(giftInfoDTOStaff.getTimeend());
            }
            if (giftInfoDTOStaff.getFlowerSizeid() != null)
                gift.setFlowersizeid(flowerSizeService.findFlowerSizeByID(giftInfoDTOStaff.getFlowerSizeid()));
            gift.setDescription(giftInfoDTOStaff.getDescription());
            gift.setStatus(giftInfoDTOStaff.getStatus());
            gift.setPercent(giftInfoDTOStaff.getPercent());
            gift.setRollbarid(rollBar);
            giftRepository.save(gift);


        }
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteRollBar ( @PathVariable int id)
    {
        RollBar rollBar = rollBarService.findRollBarByIdByAdmin(id);
        if (rollBar.getStatus() == Status.ENABLE)
            rollBar.setStatus(Status.DISABLE);
        else
            rollBar.setStatus(Status.ENABLE);
        rollBarRepository.save(rollBar);
        return ResponseEntity.ok("Success");
    }

}
