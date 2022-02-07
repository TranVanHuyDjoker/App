package com.example.app.services;

import com.example.app.dto.EmployeeDto;
import com.example.app.entities.Employee;
import org.springframework.data.domain.Page;

import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDto convertToDto(Optional<Employee> e);

    List<Employee> getAllEmployee();

    boolean exitsByEmail(String email);

    void insertEmployee(Employee employee);

    Optional<Employee> findEmployeeId(Long id);

    Employee saveEmployee(Employee e);

    void deleteEmployee(Long id);

    boolean existById(Long id);

    List<Employee> searchEmployee(String keyword);

    Page<Employee> getPageEmployee(String Direction, String sortField, int page, int size);

}
