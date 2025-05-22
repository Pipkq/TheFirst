package com.demo.controller;

import com.demo.entity.GuestCheckIn;
import com.demo.service.GuestCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/checkin")
public class GuestCheckInController {

    private final GuestCheckInService guestCheckInService;

    @Autowired
    public GuestCheckInController(GuestCheckInService guestCheckInService) {
        this.guestCheckInService = guestCheckInService;
    }

    @GetMapping("/list")
    public String listGuestCheckIns(Model model) {
        model.addAttribute("checkIns", guestCheckInService.getAllGuestCheckIns());
        return "checkin-list";
    }

    @GetMapping("/current")
    public String listCurrentGuests(Model model) {
        model.addAttribute("checkIns", guestCheckInService.getCurrentGuests());
        return "checkin-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("guestCheckIn", new GuestCheckIn());
        return "checkin-form";
    }

    @PostMapping("/save")
    public String saveGuestCheckIn(@ModelAttribute GuestCheckIn guestCheckIn, Model model) {
        try {
            // 设置默认押金和总金额（实际应用中应根据房间类型计算）
            if (guestCheckIn.getDeposit() == null) {
                guestCheckIn.setDeposit(new BigDecimal("500.00"));
            }
            if (guestCheckIn.getTotalAmount() == null) {
                // 简单计算：假设每天200元
                long days = guestCheckIn.getStartDate().until(guestCheckIn.getEndDate()).getDays();
                guestCheckIn.setTotalAmount(new BigDecimal(days * 200));
            }

            guestCheckInService.saveGuestCheckIn(guestCheckIn);
            return "redirect:/api/checkin/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("guestCheckIn", guestCheckIn);
            return "checkin-form";
        }
    }

    @GetMapping("/checkout/{id}")
    public String checkOutGuest(@PathVariable Long id) {
        guestCheckInService.checkOutGuest(id);
        return "redirect:/api/checkin/current";
    }

    @GetMapping("/delete/{id}")
    public String deleteGuestCheckIn(@PathVariable Long id) {
        guestCheckInService.deleteGuestCheckIn(id);
        return "redirect:/api/checkin/list";
    }

    @GetMapping("/search")
    public String searchGuestCheckIns(
            @RequestParam(required = false) String guestName,
            @RequestParam(required = false) String idCard,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) String status,
            Model model) {

        List<GuestCheckIn> checkIns = guestCheckInService.searchGuestCheckIns(
                guestName, idCard, phone, roomNumber, status);

        model.addAttribute("checkIns", checkIns);
        return "checkin-list";
    }
}
