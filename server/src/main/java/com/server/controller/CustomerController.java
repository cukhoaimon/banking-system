package com.server.controller;

import com.server.model.Customer;
import com.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    ResponseEntity<?> getCustomer(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String value
    ) {
        ResponseEntity<?> response = null;

        try {
            if (type == null) {
                List<Customer> customers = new ArrayList<Customer>(customerService.findAll());

                if (customers.isEmpty()) {
                    response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    response = new ResponseEntity<>(customers, HttpStatus.OK);
                }

                return response;
            }

            // type is not null
            if (value == null) {
                response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

                return response;
            }


            Optional<Customer> customer = switch (type) {
                case "name" -> customerService.findByName(value);
                case "email" -> customerService.findByEmail(value);
                case "phone" -> customerService.findByPhone(value);
                default -> Optional.empty();
            };
            response = new ResponseEntity<>(customer, HttpStatus.OK);
            return response;

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        ResponseEntity<?> response;

        try {
            Customer _customer = customerService.save(
                    new Customer(
                            customer.getId(),
                            customer.getName(),
                            customer.getEmail(),
                            customer.getPhone(),
                            customer.getAddress(),
                            customer.getAccount_ids()));

            response = new ResponseEntity<>(_customer, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
