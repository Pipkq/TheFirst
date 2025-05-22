package com.demo.service;

import com.demo.entity.ServiceRecord;
import com.demo.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceRecordService {
    private final ServiceRecordRepository serviceRecordRepository;

    @Autowired
    public ServiceRecordService(ServiceRecordRepository serviceRecordRepository) {
        this.serviceRecordRepository = serviceRecordRepository;
    }

    public List<ServiceRecord> getAllServiceRecords() {
        return serviceRecordRepository.findAll();
    }

    public ServiceRecord getServiceRecordById(Long id) {
        return serviceRecordRepository.findById(id).orElse(null);
    }

    public ServiceRecord saveServiceRecord(ServiceRecord serviceRecord) {
        return serviceRecordRepository.save(serviceRecord);
    }

    public void deleteServiceRecord(Long id) {
        serviceRecordRepository.deleteById(id);
    }

    public List<ServiceRecord> getServiceRecordsByEmployee(Long employeeId) {
        return serviceRecordRepository.findByEmployeeId(employeeId);
    }

    public List<ServiceRecord> getServiceRecordsByRoom(String roomNumber) {
        return serviceRecordRepository.findByRoomNumber(roomNumber);
    }

    public List<ServiceRecord> getServiceRecordsByDateRange(LocalDateTime start, LocalDateTime end) {
        return serviceRecordRepository.findByServiceTimeBetween(start, end);
    }
}