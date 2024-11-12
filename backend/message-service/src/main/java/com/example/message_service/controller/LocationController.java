package com.example.message_service.controller;
import com.example.message_service.entity.Location;
import com.example.message_service.fegin.EmergencyContactInterface;
import com.example.message_service.repository.LocationRepository;
import com.example.message_service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    EmergencyContactInterface emergencyContactInterface;

    @Autowired
    MessageService messageService;

    @Autowired
    LocationRepository locationRepository;

    @PostMapping("/share")
    public void shareLocation(@RequestBody Location request) {

        String message = "Emergency: I am here at this location: https://www.google.com/maps?q=" + request.getLat() + "," + request.getLng();

        locationRepository.save(request);

        messageService.sendSms(emergencyContactInterface.getContactByUserId(request.getUserId()),message);
    }

}
