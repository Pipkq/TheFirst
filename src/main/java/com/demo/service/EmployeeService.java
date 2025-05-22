package com.demo.service;

import com.demo.entity.Employee;
import com.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        if (employee.getEmployeeId() == null) {
            if (existsByIdCard(employee.getIdCard())) {
                throw new IllegalArgumentException("该身份证号已存在");
            }
            if (existsByPhone(employee.getPhone())) {
                throw new IllegalArgumentException("该电话号码已存在");
            }
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> searchEmployees(Long employeeId, String name, String position) {
        return employeeRepository.searchEmployees(employeeId, name, position);
    }

    public boolean existsByIdCard(String idCard) {
        return employeeRepository.existsByIdCard(idCard);
    }

    public boolean existsByPhone(String phone) {
        return employeeRepository.existsByPhone(phone);
    }

    public void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("员工姓名不能为空");
        }
        if (employee.getPhone() == null || employee.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("电话号码不能为空");
        }
        if (employee.getIdCard() == null || employee.getIdCard().trim().isEmpty()) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("职位不能为空");
        }
        if (employee.getSalary() == null || employee.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("工资必须大于0");
        }
    }
}