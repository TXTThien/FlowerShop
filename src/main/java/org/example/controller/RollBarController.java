package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlowerSizeDTO;
import org.example.dto.GiftInfoDTO;
import org.example.dto.RollBarInfoDTO;
import org.example.entity.Attendance;
import org.example.entity.FlowerSize;
import org.example.entity.Gift;
import org.example.entity.RollBar;
import org.example.service.IAttendanceService;
import org.example.service.IFlowerSizeService;
import org.example.service.IGiftService;
import org.example.service.IRollBarService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rollbar")
@RequiredArgsConstructor
public class RollBarController {
    private final IRollBarService rollBarService;
    private final IGiftService giftService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAttendanceService attendanceService;
    @GetMapping("/info")
    private ResponseEntity<?> infoBar ()
    {
        List<RollBar> rollBars = rollBarService.findAll();
        List<RollBarInfoDTO> rollBarInfoDTOS = new ArrayList<>();
        for (RollBar rollBar: rollBars)
        {
            RollBarInfoDTO rollBarInfoDTO = selectRollBarInfoDTO(rollBar.getId());
            rollBarInfoDTOS.add(rollBarInfoDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("rollBarInfoDTOS", rollBarInfoDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> detailBar (@PathVariable int id)
    {
        int account = getIDAccountFromAuthService.common();
        int month = LocalDateTime.now().getMonthValue();
        List<Attendance> attendanceList = attendanceService.findAttendanceByAccountAndMonth(account,month);
        int days = attendanceList.size();
        RollBarInfoDTO rollBarInfoDTO = selectRollBarInfoDTO(id);
        Map<String, Object> response = new HashMap<>();
        response.put("days",days);
        response.put("rollBarInfoDTO", rollBarInfoDTO);
        return ResponseEntity.ok(response);
    }

    private RollBarInfoDTO selectRollBarInfoDTO (int id){
        RollBar rollBar = rollBarService.findRollBarById(id);

        RollBarInfoDTO rollBarInfoDTO = new RollBarInfoDTO();

        rollBarInfoDTO.setColor(rollBar.getColor());
        rollBarInfoDTO.setDays(rollBar.getDays());
        rollBarInfoDTO.setId(rollBar.getId());
        rollBarInfoDTO.setName(rollBar.getName());

        List<GiftInfoDTO> giftInfoDTOList = new ArrayList<>();
        List <Gift> gifts = giftService.findGiftsByRollBar(rollBar);

        for (Gift gift : gifts)
        {
            GiftInfoDTO giftInfoDTO = new GiftInfoDTO();
            giftInfoDTO.setPercent(gift.getPercent());
            giftInfoDTO.setName(gift.getName());
            giftInfoDTO.setId(gift.getId());
            if (gift.getDiscountpercent() != null)
                giftInfoDTO.setDiscountpercent(gift.getDiscountpercent());
            giftInfoDTO.setTypegift(String.valueOf(gift.getTypeGift()));
            if (gift.getDescription() != null)
                giftInfoDTO.setDescription(gift.getDescription());
            if (gift.getFlowersizeid() != null)
            {
                FlowerSizeDTO flowerSizeDTO = new FlowerSizeDTO();
                flowerSizeDTO.setFlowerSizeID(gift.getFlowersizeid().getFlower().getFlowerID());
                flowerSizeDTO.setSizeName(gift.getFlowersizeid().getFlower().getName()+" "+gift.getFlowersizeid().getSizeName());
                flowerSizeDTO.setUrl(gift.getFlowersizeid().getFlower().getImage());
                giftInfoDTO.setFlowerSizeDTO(flowerSizeDTO);
            }


            giftInfoDTOList.add(giftInfoDTO);
        }
        rollBarInfoDTO.setGiftInfoDTOList(giftInfoDTOList);
        return rollBarInfoDTO;
    }

}
