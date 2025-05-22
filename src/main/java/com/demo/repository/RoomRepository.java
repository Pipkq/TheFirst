package com.demo.repository;

import com.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {
    Room findByRoomNumber(String roomNumber);

    List<Room> findByRoomType(String roomType);

    List<Room> findByIsAvailable(boolean isAvailable);

    List<Room> findByPriceBetween(double minPrice, double maxPrice);

}