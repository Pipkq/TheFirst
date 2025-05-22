package com.demo.service;

import com.demo.entity.GuestCheckIn;
import com.demo.repository.GuestCheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestCheckInService {

    private final GuestCheckInRepository guestCheckInRepository;

    @Autowired
    public GuestCheckInService(GuestCheckInRepository guestCheckInRepository) {
        this.guestCheckInRepository = guestCheckInRepository;
    }

    public List<GuestCheckIn> getAllGuestCheckIns() {
        return guestCheckInRepository.findAll();
    }

    public GuestCheckIn getGuestCheckInById(Long id) {
        return guestCheckInRepository.findById(id).orElse(null);
    }

    @Transactional
    public GuestCheckIn saveGuestCheckIn(GuestCheckIn guestCheckIn) {
        // 验证房间是否在指定日期可用
        List<GuestCheckIn> overlapping = guestCheckInRepository.findOverlappingReservations(
                guestCheckIn.getRoomNumber(),
                guestCheckIn.getStartDate(),
                guestCheckIn.getEndDate());

        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException("该房间在指定日期已被预订");
        }

        // 设置默认值
        if (guestCheckIn.getCheckInTime() == null) {
            guestCheckIn.setCheckInTime(LocalDateTime.now());
        }
        if (guestCheckIn.getStatus() == null) {
            guestCheckIn.setStatus("CHECKED_IN");
        }

        return guestCheckInRepository.save(guestCheckIn);
    }

    @Transactional
    public GuestCheckIn checkOutGuest(Long id) {
        GuestCheckIn guestCheckIn = guestCheckInRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("无效的入住记录ID"));

        guestCheckIn.setStatus("CHECKED_OUT");
        guestCheckIn.setCheckOutTime(LocalDateTime.now());

        return guestCheckInRepository.save(guestCheckIn);
    }

    public void deleteGuestCheckIn(Long id) {
        guestCheckInRepository.deleteById(id);
    }

    public List<GuestCheckIn> searchGuestCheckIns(String guestName, String idCard, String phone, String roomNumber, String status) {
        return guestCheckInRepository.searchGuestCheckIns(guestName, idCard, phone, roomNumber, status);
    }

    public List<GuestCheckIn> getCurrentGuests() {
        return guestCheckInRepository.findByStatus("CHECKED_IN");
    }
}