package com.server.controller;

import com.mongodb.client.model.Collation;
import com.server.model.Employee;
import com.server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    ResponseEntity<?> getEmployees(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String payload
    ) {
        ResponseEntity<?> response = null;

        if (query == null) {
            List<Employee> employees = employeeService.findAll();
            if (employees.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                response = new ResponseEntity<>(employees, HttpStatus.OK);
            }
        } else {
            if (payload == null) {
                response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            else {
                Optional<List<Employee>> employees = switch (query) {
                    case "name" -> employeeService.findByName(payload);
                    case "phone" -> employeeService.findByPhone(payload);
                    case "position" -> employeeService.findByPosition(payload);
                    case "managerId" -> employeeService.findByManagerID(payload);
                    default -> throw new IllegalStateException("Unexpected value: " + query);
                };

                if (employees.isPresent()) {
                    response = new ResponseEntity<>(employees.get(), HttpStatus.OK);
                } else {
                    response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        }

        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        Employee _employee = employeeService.save(employee);
        return new ResponseEntity<>(_employee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") String id) {
        employeeService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateEmployeeById(
            @RequestBody Employee newEmployee,
            @PathVariable("id") String id
    )
    {
        Employee emp = employeeService.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setPhone(newEmployee.getPhone());
                    employee.setPosition(newEmployee.getPosition());
                    employee.setManagerId(newEmployee.getManagerId());
                    employee.setUserId(newEmployee.getUserId());
                    return employeeService.save(newEmployee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeService.save(newEmployee);
                });

        return new ResponseEntity<>(emp, HttpStatus.ACCEPTED);
    }
}