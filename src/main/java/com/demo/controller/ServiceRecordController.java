package com.demo.controller;

import com.demo.entity.ServiceRecord;
import com.demo.service.ServiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/service")
public class ServiceRecordController {
    private final ServiceRecordService serviceRecordService;

    @Autowired
    public ServiceRecordController(ServiceRecordService serviceRecordService) {
        this.serviceRecordService = serviceRecordService;
    }

    @GetMapping("/list")
    public String listServiceRecords(Model model) {
        List<ServiceRecord> serviceRecords = serviceRecordService.getAllServiceRecords();
        model.addAttribute("serviceRecords", serviceRecords);
        model.addAttribute("newServiceRecord", new ServiceRecord());
        return "list";
    }

    @PostMapping("/add")
    public String addServiceRecord(@ModelAttribute ServiceRecord serviceRecord) {
        serviceRecord.setServiceTime(LocalDateTime.now());
        serviceRecordService.saveServiceRecord(serviceRecord);
        return "redirect:/api/service/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteServiceRecord(@PathVariable Long id) {
        serviceRecordService.deleteServiceRecord(id);
        return "redirect:/api/service/list";
    }

    @GetMapping("/by-employee/{employeeId}")
    public String getServiceRecordsByEmployee(@PathVariable Long employeeId, Model model) {
        List<ServiceRecord> serviceRecords = serviceRecordService.getServiceRecordsByEmployee(employeeId);
        model.addAttribute("serviceRecords", serviceRecords);
        return "list";
    }

    @GetMapping("/by-room/{roomNumber}")
    public String getServiceRecordsByRoom(@PathVariable String roomNumber, Model model) {
        List<ServiceRecord> serviceRecords = serviceRecordService.getServiceRecordsByRoom(roomNumber);
        model.addAttribute("serviceRecords", serviceRecords);
        return "list";
    }
}