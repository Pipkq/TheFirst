package com.demo.repository;

import com.demo.entity.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByEmployeeId(Long employeeId);
    List<ServiceRecord> findByRoomNumber(String roomNumber);
    List<ServiceRecord> findByServiceTimeBetween(LocalDateTime start, LocalDateTime end);
}