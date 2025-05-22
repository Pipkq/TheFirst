package com.demo.repository;

import com.demo.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findByGuestId(Long guestId);
    Guest findByGuestName(String guestName);
    List<Guest> findByGuestNameContaining(String guestName);
    Guest findByGuestPhone(String guestPhone);
    Guest findByIdCard(String idCard);
    List<Guest> findByGuestPhoneContaining(String guestPhone);
    List<Guest> findByIdCardContaining(String idCard);
}