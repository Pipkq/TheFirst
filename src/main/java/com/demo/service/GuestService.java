package com.demo.service;

import com.demo.entity.Guest;
import com.demo.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // 新增方法：获取所有客户
    public List<Guest> findAllGuests() {
        return guestRepository.findAll();
    }

    // 其他现有方法保持不变...
    public Guest findGuestById(Long guestId) {
        return guestRepository.findByGuestId(guestId);
    }

    public Guest findGuestByName(String name) {
        return guestRepository.findByGuestName(name);
    }

    public List<Guest> findGuestsByNameContaining(String name) {
        return guestRepository.findByGuestNameContaining(name);
    }

    public Guest findGuestByPhone(String phone) {
        return guestRepository.findByGuestPhone(phone);
    }

    public List<Guest> findGuestsByPhoneContaining(String phone) {
        return guestRepository.findByGuestPhoneContaining(phone);
    }

    public Guest findGuestByIdCard(String idCard) {
        return guestRepository.findByIdCard(idCard);
    }

    public List<Guest> findGuestsByIdCardContaining(String idCard) {
        return guestRepository.findByIdCardContaining(idCard);
    }

    public boolean istrue(Long guestId) {
        return guestRepository.findByGuestId(guestId) != null;
    }

    public Guest saveGuest(Guest guest) {
        // 验证身份证号是否已存在
        if (guest.getGuestId() == null) { // 新增客户
            Guest existing = guestRepository.findByIdCard(guest.getIdCard());
            if (existing != null) {
                throw new RuntimeException("身份证号已存在");
            }
        } else { // 编辑客户
            Guest existing = guestRepository.findByIdCard(guest.getIdCard());
            if (existing != null && !existing.getGuestId().equals(guest.getGuestId())) {
                throw new RuntimeException("身份证号已被其他客户使用");
            }
        }

        // 验证电话号码格式
        if (!guest.getGuestPhone().matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("电话号码格式不正确");
        }

        // 验证身份证号格式
        if (!guest.getIdCard().matches("^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$")) {
            throw new RuntimeException("身份证号格式不正确");
        }

        return guestRepository.save(guest);
    }

    public void deleteGuest(Long guestId) {
        if (!guestRepository.existsById(guestId)) {
            throw new RuntimeException("客户不存在");
        }
        guestRepository.deleteById(guestId);
    }
}