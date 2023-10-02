package com.server.service;

import com.server.model.Employee;
import com.server.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Optional<List<Employee>> findAll() {
        return Optional.of(employeeRepository.findAll());
    }

    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    public Optional<List<Employee>> findByName(String name) {
        return Optional.of(employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getName().equals(name))
                .collect(Collectors.toList()));
    }

    public Optional<List<Employee>> findByPhone(String phone) {
        return Optional.of(employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getPhone().equals(phone))
                .collect(Collectors.toList()));
    }

    public Optional<List<Employee>> findByPosition(String position) {
        return Optional.of(employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getPosition().equals(position))
                .collect(Collectors.toList()));
    }

    public Optional<List<Employee>> findByManagerID(String managerId) {
        return Optional.of(employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getManagerId().equals(managerId))
                .collect(Collectors.toList()));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteByID(String id) {
        employeeRepository.deleteById(id);
    }

    public void deleteByName(String name) {
    }
}
