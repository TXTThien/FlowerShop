package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.service.IEventFlowerService;
import org.example.service.IEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/event")
@RequiredArgsConstructor
public class StaffEventController {
    private final IEventFlowerService eventFlowerService;
    private final IEventService eventService;

//    @GetMapping("")
//    public ResponseEntity<?> getEvent ()
//    {
//
//    }
}
