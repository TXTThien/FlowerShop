package org.example.controller.Staff;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateEventDTO;
import org.example.dto.EventFlowerDTO;
import org.example.dto.FlowerSizeDTO;
import org.example.entity.Event;
import org.example.entity.EventFlower;
import org.example.entity.Flower;
import org.example.entity.FlowerSize;
import org.example.entity.enums.Status;
import org.example.repository.EventFlowerRepository;
import org.example.repository.EventRepository;
import org.example.repository.FlowerSizeRepository;
import org.example.service.IEventFlowerService;
import org.example.service.IEventService;
import org.example.service.IFlowerService;
import org.example.service.IFlowerSizeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/staff/event")
@RequiredArgsConstructor
public class StaffEventController {
    private final IEventFlowerService eventFlowerService;
    private final IEventService eventService;
    private final EventRepository eventRepository;
    private final EventFlowerRepository eventFlowerRepository;
    private final IFlowerSizeService flowerSizeService;
    private final FlowerSizeRepository flowerSizeRepository;
    private final IFlowerService flowerService;

    @GetMapping("")
    public ResponseEntity<?> getEvent() {
        List<Event> eventList = eventRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("Event", eventList);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getflowersize")
    public ResponseEntity<?> getFlowerSize(){
        List<EventFlowerDTO> allFlowers = flowerService.findAll()
                .stream()
                .map(this::convertToEventFlowerDTO)
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("AllFlower", allFlowers);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailEvent(@PathVariable int id) {
        Event event = eventService.findEventByID(id);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }

        List<EventFlowerDTO> eventFlowerDTOS = eventFlowerService.findEventFlowerByEventIDForStaff(id)
                .stream()
                .map(this::convertToEventFlowerDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("Event", event);
        response.put("EventFlower", eventFlowerDTOS);

        return ResponseEntity.ok(response);
    }


    private EventFlowerDTO convertToEventFlowerDTO(EventFlower eventFlower) {
        EventFlowerDTO eventFlowerDTO = new EventFlowerDTO();
        eventFlowerDTO.setFlowerID(eventFlower.getFlowerSize().getFlower().getFlowerID());
        eventFlowerDTO.setIdEventFlower(eventFlower.getId());
        eventFlowerDTO.setFlowerName(eventFlower.getFlowerSize().getFlower().getName());
        eventFlowerDTO.setSizeChoose(eventFlower.getFlowerSize().getSizeName());
        eventFlowerDTO.setSaleOff(eventFlower.getSaleoff());
        eventFlowerDTO.setImageurl(eventFlower.getFlowerSize().getFlower().getImage());
        List<FlowerSizeDTO> sizeList = convertToFlowerSizeDTOList(
                flowerSizeService.findFlowerSizeByProductID(eventFlower.getFlowerSize().getFlower().getFlowerID())
        );
        eventFlowerDTO.setSize(sizeList);

        return eventFlowerDTO;
    }

    private EventFlowerDTO convertToEventFlowerDTO(Flower flower) {
        EventFlowerDTO eventFlowerDTO = new EventFlowerDTO();
        eventFlowerDTO.setFlowerName(flower.getName());

        List<FlowerSizeDTO> sizeList = convertToFlowerSizeDTOList(
                flowerSizeService.findFlowerSizeByProductID(flower.getFlowerID())
        );
        eventFlowerDTO.setSize(sizeList);

        return eventFlowerDTO;
    }

    private List<FlowerSizeDTO> convertToFlowerSizeDTOList(List<FlowerSize> flowerSizes) {
        if (flowerSizes == null) return Collections.emptyList();

        return flowerSizes.stream()
                .map(flowerSize -> {
                    FlowerSizeDTO sizeDTO = new FlowerSizeDTO();
                    sizeDTO.setSizeName(flowerSize.getSizeName());
                    sizeDTO.setFlowerSizeID(flowerSize.getFlowerSizeID());
                    return sizeDTO;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventDTO createEventDTO) {
        // Tạo sự kiện mới
        Event event = new Event();
        event.setName(createEventDTO.getEventName());
        event.setColor(createEventDTO.getColor());
        event.setDescription(createEventDTO.getDescription());
        event.setStart(createEventDTO.getStart());
        event.set_manual(false);
        event.setEnd(createEventDTO.getEnd());

        if (createEventDTO.getStart().isBefore(LocalDateTime.now()) && LocalDateTime.now().isBefore(createEventDTO.getEnd())) {
            event.setStatus(Status.ENABLE);
        } else {
            event.setStatus(Status.DISABLE);
        }

        eventRepository.save(event);

        List<EventFlowerDTO> eventFlowers = createEventDTO.getEventFlowerDTOS();
        if (eventFlowers != null && !eventFlowers.isEmpty()) {
            List<EventFlower> eventFlowerList = new ArrayList<>();

            for (EventFlowerDTO eventFlowerDTO : eventFlowers) {
                List<FlowerSize> flowerSizes = new ArrayList<>();

                // Nếu SizeID được chọn là -1, lấy tất cả các FlowerSize phù hợp
                if (eventFlowerDTO.getSizeIDChoose() == -1) {
                    if (eventFlowerDTO.getFlowerID() != -1) {
                        flowerSizes = flowerSizeService.findFlowerSizeByProductID(eventFlowerDTO.getFlowerID());
                    } else {
                        flowerSizes = flowerSizeRepository.findAll();
                    }
                } else {
                    FlowerSize flowerSize = flowerSizeService.findFlowerSizeByID(eventFlowerDTO.getSizeIDChoose());
                    if (flowerSize == null) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FlowerSize không tồn tại");
                    }
                    flowerSizes.add(flowerSize);
                }

                for (FlowerSize flowerSize : flowerSizes) {
                    EventFlower eventFlower = new EventFlower();
                    eventFlower.setEvent(event);
                    eventFlower.setFlowerSize(flowerSize);
                    eventFlower.setSaleoff(eventFlowerDTO.getSaleOff());
                    eventFlower.setStatus(event.getStatus());
                    eventFlowerList.add(eventFlower);
                }
            }

            // Lưu tất cả vào DB
            eventFlowerRepository.saveAll(eventFlowerList);
        }

        // Trả về phản hồi thành công
        return ResponseEntity.ok("Sự kiện được tạo thành công");
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@RequestBody CreateEventDTO createEventDTO, @PathVariable int id) {
        Event event = eventService.findEventByID(id);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sự kiện");
        }

        // Cập nhật thông tin sự kiện
        event.setName(createEventDTO.getEventName());
        event.setColor(createEventDTO.getColor());
        event.setDescription(createEventDTO.getDescription());
        event.setStart(createEventDTO.getStart());
        event.setEnd(createEventDTO.getEnd());
        event.set_manual(false);

        if (createEventDTO.getStart().isBefore(LocalDateTime.now()) && LocalDateTime.now().isBefore(createEventDTO.getEnd())) {
            event.setStatus(Status.ENABLE);
        } else {
            event.setStatus(Status.DISABLE);
        }

        eventRepository.save(event);

        // Xử lý danh sách EventFlower
        List<EventFlowerDTO> eventFlowerDTOS = createEventDTO.getEventFlowerDTOS();
        if (eventFlowerDTOS != null && !eventFlowerDTOS.isEmpty()) {
            List<EventFlower> eventFlowerList = new ArrayList<>();

            for (EventFlowerDTO eventFlowerDTO : eventFlowerDTOS) {
                List<FlowerSize> flowerSizes = new ArrayList<>();

                if (eventFlowerDTO.getSizeIDChoose() == -1) {
                    if (eventFlowerDTO.getFlowerID() != -1) {
                        flowerSizes = flowerSizeService.findFlowerSizeByProductID(eventFlowerDTO.getFlowerID());
                    } else {
                        flowerSizes = flowerSizeRepository.findAll();
                    }
                } else {
                    FlowerSize flowerSize = flowerSizeService.findFlowerSizeByID(eventFlowerDTO.getSizeIDChoose());
                    if (flowerSize == null) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy FlowerSize với ID: " + eventFlowerDTO.getSizeIDChoose());
                    }
                    flowerSizes.add(flowerSize);
                }

                for (FlowerSize flowerSize : flowerSizes) {
                    EventFlower eventFlower;

                    if (eventFlowerDTO.getIdEventFlower() != null) {
                        eventFlower = eventFlowerService.findEventFlowerByEventFlowerID(eventFlowerDTO.getIdEventFlower());
                        if (eventFlower == null) {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy EventFlower với ID: " + eventFlowerDTO.getIdEventFlower());
                        }
                    } else {
                        eventFlower = new EventFlower();
                        eventFlower.setEvent(event);
                    }

                    eventFlower.setFlowerSize(flowerSize);
                    eventFlower.setSaleoff(eventFlowerDTO.getSaleOff());
                    eventFlower.setStatus(event.getStatus());
                    eventFlowerList.add(eventFlower);
                }
            }

            eventFlowerRepository.saveAll(eventFlowerList);
        }

        return ResponseEntity.ok("Sự kiện cập nhật thành công");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id) {
        Event event = eventService.findEventByID(id);
        if (!event.is_manual()) {
            event.setStatus(Status.DISABLE);
            event.set_manual(true);
        } else {
            if (event.getStart().isBefore(LocalDateTime.now())
                    && LocalDateTime.now().isBefore(event.getEnd())) {
                event.setStatus(Status.ENABLE);
            } else {
                event.setStatus(Status.DISABLE);
            }
            event.set_manual(false);
        }
        eventRepository.save(event);
        return ResponseEntity.ok("Sự kiện cập nhật thành công");
    }

}
