package com.demo.controller;

import com.demo.entity.Guest;
import com.demo.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/guest")
public class GuestController {
    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/search")
    public String guestSearch(
            @RequestParam(value = "id", required = false) Long guestId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "idCard", required = false) String idCard,
            Model model) {

        // 默认显示所有客户
        List<Guest> guests = guestService.findAllGuests();

        // 根据搜索条件过滤
        if (guestId != null) {
            Guest guest = guestService.findGuestById(guestId);
            if (guest != null) {
                guests = List.of(guest);
            } else {
                model.addAttribute("error", "未找到ID为 " + guestId + " 的客户");
            }
        } else if (name != null && !name.isEmpty()) {
            guests = guestService.findGuestsByNameContaining(name);
            if (guests.isEmpty()) {
                model.addAttribute("error", "未找到姓名包含 '" + name + "' 的客户");
            }
        } else if (phone != null && !phone.isEmpty()) {
            guests = guestService.findGuestsByPhoneContaining(phone);
            if (guests.isEmpty()) {
                model.addAttribute("error", "未找到电话包含 '" + phone + "' 的客户");
            }
        } else if (idCard != null && !idCard.isEmpty()) {
            guests = guestService.findGuestsByIdCardContaining(idCard);
            if (guests.isEmpty()) {
                model.addAttribute("error", "未找到身份证包含 '" + idCard + "' 的客户");
            }
        }

        model.addAttribute("guests", guests);
        return "GuestList";
    }

    @GetMapping("/Main")
    public String main() {
        return "Main";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "GuestForm";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long guestId, Model model) {
        Guest guest = guestService.findGuestById(guestId);
        if (guest == null) {
            model.addAttribute("error", "客户不存在");
            return "redirect:/api/guest/search";
        }
        model.addAttribute("guest", guest);
        return "GuestForm";
    }

    @PostMapping("/save")
    public String saveGuest(@ModelAttribute Guest guest, Model model) {
        try {
            guestService.saveGuest(guest);
            return "redirect:/api/guest/search";
        } catch (Exception e) {
            model.addAttribute("error", "保存客户信息失败: " + e.getMessage());
            return "GuestForm";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable("id") Long guestId, Model model) {
        try {
            guestService.deleteGuest(guestId);
        } catch (Exception e) {
            model.addAttribute("error", "删除客户失败: " + e.getMessage());
        }
        return "redirect:/api/guest/search";
    }
}
