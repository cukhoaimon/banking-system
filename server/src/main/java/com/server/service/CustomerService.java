package com.server.service;

import com.server.model.Customer;
import com.server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByName(String name) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Customer> findByPhone(String phone) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getPhone().equals(phone))
                .findFirst();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteByEmail(String email) {
        Optional<Customer> target = this.findByEmail(email);

        target.ifPresent(customer -> customerRepository.deleteById(customer.getId()));
    }

    public void deleteByName(String name) {
        Optional<Customer> target = this.findByName(name);

        target.ifPresent(customer -> customerRepository.deleteById(customer.getId()));
    }
}
