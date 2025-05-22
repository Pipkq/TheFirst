package com.demo.repository;

import com.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContaining(String name);

    List<Employee> findByPosition(String position);  // 参数改为String

    @Query("SELECT e FROM Employee e WHERE " +
            "(:employeeId IS NULL OR e.employeeId = :employeeId) AND " +
            "(:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:position IS NULL OR e.position = :position)")
    List<Employee> searchEmployees(
            @Param("employeeId") Long employeeId,
            @Param("name") String name,
            @Param("position") String position);  // 参数改为String

    boolean existsByIdCard(String idCard);

    boolean existsByPhone(String phone);
}