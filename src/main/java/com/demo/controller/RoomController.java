package com.demo.controller;

import com.demo.entity.Room;
import com.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String getAllRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "RoomList";
    }

    @PostMapping("/updateStatus")
    public String updateRoomStatus(@RequestParam String roomNumber,
                                   @RequestParam boolean isAvailable,
                                   Model model) {
        Room updatedRoom = roomService.updateRoomStatus(roomNumber, isAvailable);
        if (updatedRoom != null) {
            model.addAttribute("message", "房间状态更新成功");
        } else {
            model.addAttribute("error", "房间不存在");
        }
        return "redirect:/api/room/list";
    }

    @GetMapping("/edit/{roomNumber}")
    public String showEditForm(@PathVariable String roomNumber, Model model) {
        Room room = roomService.getRoomByNumber(roomNumber);
        if (room != null) {
            model.addAttribute("room", room);
            return "EditRoom";
        }
        return "redirect:/api/room/list";
    }

    @PostMapping("/update")
    public String updateRoom(@ModelAttribute Room room, Model model) {
        roomService.saveRoom(room);
        model.addAttribute("message", "房间信息更新成功");
        return "redirect:/api/room/list";
    }

    @GetMapping("/search")
    public String searchRooms(@RequestParam(required = false) String roomNumber,
                              @RequestParam(required = false) String roomType,
                              @RequestParam(required = false) Boolean isAvailable,
                              @RequestParam(required = false) Double minPrice,
                              @RequestParam(required = false) Double maxPrice,
                              Model model) {

        if (roomNumber != null && !roomNumber.isEmpty()) {
            Room room = roomService.getRoomByNumber(roomNumber);
            model.addAttribute("rooms", room != null ? List.of(room) : List.of());
        } else if (roomType != null && !roomType.isEmpty()) {
            model.addAttribute("rooms", roomService.getRoomsByType(roomType));
        } else if (isAvailable != null) {
            model.addAttribute("rooms", roomService.getRoomsByAvailability(isAvailable));
        } else if (minPrice != null && maxPrice != null) {
            model.addAttribute("rooms", roomService.getRoomsByPriceRange(minPrice, maxPrice));
        } else {
            model.addAttribute("rooms", roomService.getAllRooms());
        }

        return "RoomList";
    }
}