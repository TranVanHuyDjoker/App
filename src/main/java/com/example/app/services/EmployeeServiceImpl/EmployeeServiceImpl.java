package com.example.app.services.EmployeeServiceImpl;

import com.example.app.dto.EmployeeDto;
import com.example.app.entities.Employee;
import com.example.app.repositories.EmployeeRepository;
import com.example.app.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto convertToDto(Optional<Employee> e) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setPhone(e.get().getPhone());
        employeeDto.setAddress(e.get().getAddress());
        employeeDto.setEmail(e.get().getEmail());
        employeeDto.setId(e.get().getId());
        return employeeDto;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public boolean exitsByEmail(String email) {
        return employeeRepository.existsEmployeeByEmail(email);
    }

    @Override
    public void insertEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findEmployeeId(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee e) {
       return employeeRepository.save(e);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return employeeRepository.existsEmployeeById(id);
    }

    @Override
    public List<Employee> searchEmployee(String keyword) {
        return employeeRepository.findEmployee(keyword);
    }

    @Override
    public Page<Employee> getPageEmployee(String Direction, String sortField, int page, int size) {
        Sort.Direction direction = "ASC".equals(Direction)? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction,sortField));
        return employeeRepository.findAll(pageable);
    }


}
