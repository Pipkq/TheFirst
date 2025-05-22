package com.demo.service;

import com.demo.entity.Room;
import com.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    public List<Room> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    public List<Room> getRoomsByAvailability(boolean isAvailable) {
        return roomRepository.findByIsAvailable(isAvailable);
    }

    public List<Room> getRoomsByPriceRange(double minPrice, double maxPrice) {
        return roomRepository.findByPriceBetween(minPrice, maxPrice);
    }


    public Room updateRoomStatus(String roomNumber, boolean isAvailable) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room != null) {
            room.setAvailable(isAvailable);
            return roomRepository.save(room);
        }
        return null;
    }

    public boolean roomExists(String roomNumber) {
        return roomRepository.existsById(roomNumber);
    }
}