// test webhook
package com.attendance.attendancesystem;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @GetMapping("/status")
    public Map<String, String> status() {

        Map<String, String> response = new HashMap<>();
        response.put("status", "Attendance Service Running");

        return response;
    }

    @PostMapping("/checkin")
    public Map<String, String> checkIn() {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Check-in successful");

        return response;
    }
}