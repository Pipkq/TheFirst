package com.demo.repository;

import com.demo.entity.GuestCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuestCheckInRepository extends JpaRepository<GuestCheckIn, Long> {

    List<GuestCheckIn> findByGuestNameContaining(String guestName);

    List<GuestCheckIn> findByIdCard(String idCard);

    List<GuestCheckIn> findByPhone(String phone);

    List<GuestCheckIn> findByRoomNumber(String roomNumber);

    List<GuestCheckIn> findByStatus(String status);

    @Query("SELECT g FROM GuestCheckIn g WHERE " +
            "(:guestName IS NULL OR g.guestName LIKE %:guestName%) AND " +
            "(:idCard IS NULL OR g.idCard = :idCard) AND " +
            "(:phone IS NULL OR g.phone = :phone) AND " +
            "(:roomNumber IS NULL OR g.roomNumber = :roomNumber) AND " +
            "(:status IS NULL OR g.status = :status)")
    List<GuestCheckIn> searchGuestCheckIns(
            @Param("guestName") String guestName,
            @Param("idCard") String idCard,
            @Param("phone") String phone,
            @Param("roomNumber") String roomNumber,
            @Param("status") String status);

    @Query("SELECT g FROM GuestCheckIn g WHERE " +
            "g.roomNumber = :roomNumber AND " +
            "((g.startDate <= :endDate AND g.endDate >= :startDate) AND " +
            "g.status = 'CHECKED_IN')")
    List<GuestCheckIn> findOverlappingReservations(
            @Param("roomNumber") String roomNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}