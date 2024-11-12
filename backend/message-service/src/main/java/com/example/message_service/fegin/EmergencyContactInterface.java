package com.example.message_service.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EmergencyContact",url = "http://localhost:9898/api/emergency-contacts")
public interface EmergencyContactInterface {
    @GetMapping("/userId/{userId}")
    public String getContactByUserId(@PathVariable long userId);
}
