package com.example.app.controllers;

import com.example.app.dto.EmployeeDto;
import com.example.app.entities.Employee;
import com.example.app.entities.ResObj;
import com.example.app.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1")
public class EmployeeControllers {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/allEmployee")
    ResponseEntity<ResObj> getAllEmployee(){
        List<Employee> e = employeeService.getAllEmployee();
        List<EmployeeDto> employeeDtos = e.stream()
                .map(employee -> employeeService.convertToDto(java.util.Optional.ofNullable(employee)))
                .collect(Collectors.toList());

        if (e.size()>=1){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj("Successful",employeeDtos)
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResObj("Database Empty","")
            );
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResObj> insertEmployee(@RequestBody Employee e){
        boolean flag = employeeService.exitsByEmail(e.getEmail());
        if (flag) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResObj("Employee already exists", "")
            );
        }else {
            employeeService.insertEmployee(e);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj("Successful",e)
            );
        }
    }

    @PutMapping("/updateEmployee/{id}")
    ResponseEntity<ResObj> updateEmployee(@RequestBody Employee newE, @PathVariable Long id){
        Employee employee = employeeService.findEmployeeId(id)
                .map(employee1 -> {
                    employee1.setPassword(newE.getPassword());
                    employee1.setPhone(newE.getPhone());
                    employee1.setAddress(newE.getAddress());
                    employee1.setEmail(newE.getEmail());

                    return employeeService.saveEmployee(employee1);
                }).orElseGet(()->{
                    newE.setId(id);
                    return employeeService.saveEmployee(newE);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResObj("Update Successfully", employee)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResObj> deleteEmployee(@PathVariable Long id){
        boolean flag= employeeService.existById(id);
        if(flag){
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj("Delete Successful","")
            );

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResObj("Data emty","")
            );
        }
    }

    @GetMapping("/search")
    ResponseEntity<ResObj> searchEmployee(@RequestParam String keyword){
        List<Employee> e = employeeService.searchEmployee(keyword);
        if(e.size()>=1){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj("Successful",
                            e.stream().map(employee -> employeeService.convertToDto(Optional.ofNullable(employee))))
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj("Employee already exists","")
            );
        }
    }

    @GetMapping("/getMany")
    ResponseEntity<?> getMany(@RequestParam String Direction,
                              @RequestParam String sortField,
                              @RequestParam int page,
                              @RequestParam int size) {
        Page<Employee> employeePage = employeeService.getPageEmployee(Direction, sortField, page, size);
        if (employeePage.getSize() >= 1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj("Successful", employeePage.stream()
                            .map(employee -> employeeService.convertToDto(Optional.ofNullable(employee)))
                            .collect(Collectors.toList()))
            );

        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResObj("Employee already exists", "")
            );
        }

    }

}
